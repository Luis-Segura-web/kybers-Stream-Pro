package com.kybers.streampro.domain.usecase

import com.kybers.streampro.domain.model.LiveStream
import com.kybers.streampro.domain.repository.XtreamRepository
import javax.inject.Inject

class GetLiveStreamsUseCase @Inject constructor(
    private val repository: XtreamRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<List<LiveStream>> {
        return repository.getLiveStreams(username, password)
    }
}