package com.kybers.streampro.data.repository

import com.kybers.streampro.data.local.dao.FavoriteDao
import com.kybers.streampro.data.local.entity.FavoriteEntity
import com.kybers.streampro.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {
    
    override fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }
    
    override fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>> {
        return favoriteDao.getFavoritesByType(type)
    }
    
    override suspend fun isFavorite(streamId: Int): Boolean {
        return favoriteDao.isFavorite(streamId)
    }
    
    override suspend fun addFavorite(streamId: Int, streamType: String, name: String, icon: String?) {
        val favorite = FavoriteEntity(
            streamId = streamId,
            streamType = streamType,
            name = name,
            icon = icon
        )
        favoriteDao.addFavorite(favorite)
    }
    
    override suspend fun removeFavorite(streamId: Int) {
        favoriteDao.removeFavoriteById(streamId)
    }
}