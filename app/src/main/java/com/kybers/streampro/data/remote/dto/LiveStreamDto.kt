package com.kybers.streampro.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.kybers.streampro.domain.model.LiveStream

@JsonClass(generateAdapter = true)
data class LiveStreamDto(
    @Json(name = "num") val num: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "stream_id") val streamId: Int?,
    @Json(name = "stream_icon") val streamIcon: String?,
    @Json(name = "category_id") val categoryId: String?,
    @Json(name = "tv_archive") val tvArchive: Int?,
    @Json(name = "direct_source") val directSource: String?,
    @Json(name = "tv_archive_url") val tvArchiveUrl: String?,
    @Json(name = "epg_channel_id") val epgChannelId: String?,
    @Json(name = "added") val added: String?,
    @Json(name = "is_adult") val isNsfw: String?
) {
    fun toDomain() = LiveStream(
        num = num ?: 0,
        name = name ?: "",
        streamId = streamId ?: 0,
        streamIcon = streamIcon,
        categoryId = categoryId ?: "",
        tvArchive = tvArchive ?: 0,
        directSource = directSource,
        tvArchiveUrl = tvArchiveUrl,
        epgChannelId = epgChannelId,
        added = added,
        isNsfw = isNsfw == "1"
    )
}