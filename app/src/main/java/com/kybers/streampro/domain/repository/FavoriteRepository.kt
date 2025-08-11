package com.kybers.streampro.domain.repository

import com.kybers.streampro.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>>
    suspend fun isFavorite(streamId: Int): Boolean
    suspend fun addFavorite(streamId: Int, streamType: String, name: String, icon: String?)
    suspend fun removeFavorite(streamId: Int)
}