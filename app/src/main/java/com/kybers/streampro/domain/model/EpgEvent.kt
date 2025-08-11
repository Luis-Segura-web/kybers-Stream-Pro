package com.kybers.streampro.domain.model

data class EpgEvent(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String?,
    val startTime: Long,
    val endTime: Long,
    val category: String?
) {
    fun isCurrentlyPlaying(): Boolean {
        val now = System.currentTimeMillis() / 1000
        return now >= startTime && now <= endTime
    }
    
    fun isUpcoming(): Boolean {
        val now = System.currentTimeMillis() / 1000
        return now < startTime
    }
}