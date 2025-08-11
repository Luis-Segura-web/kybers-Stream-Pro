package com.kybers.streampro.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.kybers.streampro.domain.model.ServerInfo

@JsonClass(generateAdapter = true)
data class ServerInfoDto(
    @Json(name = "url") val url: String?,
    @Json(name = "port") val port: String?,
    @Json(name = "https_port") val httpsPort: String?,
    @Json(name = "server_protocol") val serverProtocol: String?,
    @Json(name = "rtmp_port") val rtmpPort: String?,
    @Json(name = "timezone") val timezone: String?,
    @Json(name = "timestamp_now") val timestampNow: Long?
) {
    fun toDomain() = ServerInfo(
        url = url ?: "",
        port = port ?: "",
        httpsPort = httpsPort,
        serverProtocol = serverProtocol,
        rtmpPort = rtmpPort,
        timezone = timezone,
        timestampNow = timestampNow
    )
}