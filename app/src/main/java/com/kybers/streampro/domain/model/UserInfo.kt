package com.kybers.streampro.domain.model

data class UserInfo(
    val username: String,
    val password: String,
    val status: String,
    val expDate: String?,
    val isTrial: Boolean = false,
    val activeCons: String?,
    val createdAt: String?
)