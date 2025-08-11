package com.kybers.streampro.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kybers.streampro.data.local.entity.EpgEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpgDao {
    
    @Query("SELECT * FROM epg_events WHERE channelId = :channelId AND startTime <= :currentTime AND endTime >= :currentTime ORDER BY startTime LIMIT 1")
    suspend fun getCurrentEvent(channelId: String, currentTime: Long): EpgEventEntity?
    
    @Query("SELECT * FROM epg_events WHERE channelId = :channelId AND startTime > :currentTime ORDER BY startTime LIMIT 1")
    suspend fun getNextEvent(channelId: String, currentTime: Long): EpgEventEntity?
    
    @Query("SELECT * FROM epg_events WHERE channelId = :channelId AND startTime >= :startTime AND endTime <= :endTime ORDER BY startTime")
    fun getEventsForChannel(channelId: String, startTime: Long, endTime: Long): Flow<List<EpgEventEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EpgEventEntity>)
    
    @Query("DELETE FROM epg_events WHERE endTime < :timestamp")
    suspend fun deleteOldEvents(timestamp: Long)
    
    @Query("DELETE FROM epg_events")
    suspend fun clearAll()
}