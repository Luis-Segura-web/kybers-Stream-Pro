package com.kybers.streampro.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.kybers.streampro.domain.model.UserInfo

@JsonClass(generateAdapter = true)
data class UserInfoDto(
    @Json(name = "username") val username: String?,
    @Json(name = "password") val password: String?,
    @Json(name = "status") val status: String?,
    @Json(name = "exp_date") val expDate: String?,
    @Json(name = "is_trial") val isTrial: String?,
    @Json(name = "active_cons") val activeCons: String?,
    @Json(name = "created_at") val createdAt: String?
) {
    fun toDomain() = UserInfo(
        username = username ?: "",
        password = password ?: "",
        status = status ?: "",
        expDate = expDate,
        isTrial = isTrial == "1",
        activeCons = activeCons,
        createdAt = createdAt
    )
}