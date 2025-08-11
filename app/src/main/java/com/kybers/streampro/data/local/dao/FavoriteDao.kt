package com.kybers.streampro.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kybers.streampro.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    
    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    
    @Query("SELECT * FROM favorites WHERE streamType = :type ORDER BY addedAt DESC")
    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>>
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE streamId = :streamId)")
    suspend fun isFavorite(streamId: Int): Boolean
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)
    
    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)
    
    @Query("DELETE FROM favorites WHERE streamId = :streamId")
    suspend fun removeFavoriteById(streamId: Int)
}