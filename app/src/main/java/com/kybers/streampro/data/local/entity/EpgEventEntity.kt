package com.kybers.streampro.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kybers.streampro.domain.model.EpgEvent

@Entity(tableName = "epg_events")
data class EpgEventEntity(
    @PrimaryKey val id: String,
    val channelId: String,
    val title: String,
    val description: String?,
    val startTime: Long,
    val endTime: Long,
    val category: String?
) {
    fun toDomain() = EpgEvent(
        id = id,
        channelId = channelId,
        title = title,
        description = description,
        startTime = startTime,
        endTime = endTime,
        category = category
    )
}

fun EpgEvent.toEntity() = EpgEventEntity(
    id = id,
    channelId = channelId,
    title = title,
    description = description,
    startTime = startTime,
    endTime = endTime,
    category = category
)