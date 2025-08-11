package com.kybers.streampro.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.kybers.streampro.data.local.dao.EpgDao
import com.kybers.streampro.data.local.dao.FavoriteDao
import com.kybers.streampro.data.local.entity.EpgEventEntity
import com.kybers.streampro.data.local.entity.FavoriteEntity

@Database(
    entities = [EpgEventEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class StreamDatabase : RoomDatabase() {
    
    abstract fun epgDao(): EpgDao
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        const val DATABASE_NAME = "stream_database"
    }
}