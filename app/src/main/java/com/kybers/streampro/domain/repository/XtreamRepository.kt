package com.kybers.streampro.domain.repository

import com.kybers.streampro.domain.model.*
import kotlinx.coroutines.flow.Flow

interface XtreamRepository {
    suspend fun login(host: String, port: String, username: String, password: String): Result<Pair<UserInfo, ServerInfo>>
    suspend fun getLiveStreams(username: String, password: String): Result<List<LiveStream>>
    suspend fun getLiveCategories(username: String, password: String): Result<List<Category>>
    suspend fun getVodStreams(username: String, password: String): Result<List<VodStream>>
    suspend fun getVodCategories(username: String, password: String): Result<List<Category>>
    suspend fun downloadEpg(username: String, password: String): Result<Unit>
    fun getCurrentEvent(channelId: String): Flow<EpgEvent?>
    fun getNextEvent(channelId: String): Flow<EpgEvent?>
    fun getEventsForChannel(channelId: String, startTime: Long, endTime: Long): Flow<List<EpgEvent>>
}