package com.kybers.streampro.domain.model

data class LiveStream(
    val num: Int,
    val name: String,
    val streamId: Int,
    val streamIcon: String?,
    val categoryId: String,
    val tvArchive: Int,
    val directSource: String?,
    val tvArchiveUrl: String?,
    val epgChannelId: String?,
    val added: String?,
    val isNsfw: Boolean = false
) {
    fun getStreamUrl(host: String, port: String, username: String, password: String): String {
        return "http://$host:$port/live/$username/$password/$streamId.m3u8"
    }
    
    fun getTsStreamUrl(host: String, port: String, username: String, password: String): String {
        return "http://$host:$port/live/$username/$password/$streamId.ts"
    }
}