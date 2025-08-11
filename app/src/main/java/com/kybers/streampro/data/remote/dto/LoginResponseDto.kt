package com.kybers.streampro.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseDto(
    @Json(name = "user_info") val userInfo: UserInfoDto?,
    @Json(name = "server_info") val serverInfo: ServerInfoDto?
)