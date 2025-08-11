package com.kybers.streampro.domain.model

data class VodStream(
    val num: Int,
    val name: String,
    val streamId: Int,
    val streamIcon: String?,
    val categoryId: String,
    val containerExtension: String?,
    val rating: String?,
    val plot: String?,
    val cast: String?,
    val director: String?,
    val genre: String?,
    val releaseDate: String?,
    val duration: String?,
    val youtubeTrailer: String?,
    val added: String?
) {
    fun getStreamUrl(host: String, port: String, username: String, password: String): String {
        val extension = containerExtension ?: "mp4"
        return "http://$host:$port/movie/$username/$password/$streamId.$extension"
    }
}