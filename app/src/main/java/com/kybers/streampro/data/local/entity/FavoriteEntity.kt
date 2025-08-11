package com.kybers.streampro.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val streamId: Int,
    val streamType: String, // "live", "vod", "series"
    val name: String,
    val icon: String?,
    val addedAt: Long = System.currentTimeMillis()
)