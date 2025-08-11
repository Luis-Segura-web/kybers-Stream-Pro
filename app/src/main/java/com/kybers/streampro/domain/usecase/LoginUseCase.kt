package com.kybers.streampro.domain.usecase

import com.kybers.streampro.domain.model.UserInfo
import com.kybers.streampro.domain.model.ServerInfo
import com.kybers.streampro.domain.repository.XtreamRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: XtreamRepository
) {
    suspend operator fun invoke(
        host: String,
        port: String,
        username: String,
        password: String
    ): Result<Pair<UserInfo, ServerInfo>> {
        return repository.login(host, port, username, password)
    }
}