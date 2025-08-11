package com.kybers.streampro.domain.model

data class ServerInfo(
    val url: String,
    val port: String,
    val httpsPort: String?,
    val serverProtocol: String?,
    val rtmpPort: String?,
    val timezone: String?,
    val timestampNow: Long?
)