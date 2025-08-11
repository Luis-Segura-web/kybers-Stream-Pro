package com.kybers.streampro.data.repository

import com.kybers.streampro.data.local.dao.EpgDao
import com.kybers.streampro.data.local.entity.toEntity
import com.kybers.streampro.data.remote.XtreamApi
import com.kybers.streampro.domain.model.*
import com.kybers.streampro.domain.repository.XtreamRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class XtreamRepositoryImpl @Inject constructor(
    private val api: XtreamApi,
    private val epgDao: EpgDao
) : XtreamRepository {
    
    override suspend fun login(
        host: String,
        port: String,
        username: String,
        password: String
    ): Result<Pair<UserInfo, ServerInfo>> {
        return try {
            val response = api.login(username, password)
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                val userInfo = loginResponse.userInfo?.toDomain()
                val serverInfo = loginResponse.serverInfo?.toDomain()
                
                if (userInfo != null && serverInfo != null) {
                    Result.success(Pair(userInfo, serverInfo))
                } else {
                    Result.failure(Exception("Invalid login response"))
                }
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getLiveStreams(username: String, password: String): Result<List<LiveStream>> {
        return try {
            val response = api.getLiveStreams(username, password)
            if (response.isSuccessful && response.body() != null) {
                val streams = response.body()!!.map { it.toDomain() }
                Result.success(streams)
            } else {
                Result.failure(Exception("Failed to get live streams: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getLiveCategories(username: String, password: String): Result<List<Category>> {
        return try {
            val response = api.getLiveCategories(username, password)
            if (response.isSuccessful && response.body() != null) {
                val categories = response.body()!!.map { it.toDomain() }
                Result.success(categories)
            } else {
                Result.failure(Exception("Failed to get live categories: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getVodStreams(username: String, password: String): Result<List<VodStream>> {
        // For now, return empty list as we need to create VodStreamDto
        return Result.success(emptyList())
    }
    
    override suspend fun getVodCategories(username: String, password: String): Result<List<Category>> {
        return try {
            val response = api.getVodCategories(username, password)
            if (response.isSuccessful && response.body() != null) {
                val categories = response.body()!!.map { it.toDomain() }
                Result.success(categories)
            } else {
                Result.failure(Exception("Failed to get VOD categories: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun downloadEpg(username: String, password: String): Result<Unit> {
        return try {
            val response = api.getEpgXml(username, password)
            if (response.isSuccessful && response.body() != null) {
                // Parse EPG XML and save to database
                // For now, return success - EPG parsing will be implemented later
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to download EPG: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getCurrentEvent(channelId: String): Flow<EpgEvent?> {
        val currentTime = System.currentTimeMillis() / 1000
        return kotlinx.coroutines.flow.flow {
            val event = epgDao.getCurrentEvent(channelId, currentTime)
            emit(event?.toDomain())
        }
    }
    
    override fun getNextEvent(channelId: String): Flow<EpgEvent?> {
        val currentTime = System.currentTimeMillis() / 1000
        return kotlinx.coroutines.flow.flow {
            val event = epgDao.getNextEvent(channelId, currentTime)
            emit(event?.toDomain())
        }
    }
    
    override fun getEventsForChannel(channelId: String, startTime: Long, endTime: Long): Flow<List<EpgEvent>> {
        return epgDao.getEventsForChannel(channelId, startTime, endTime)
            .map { entities -> entities.map { it.toDomain() } }
    }
}