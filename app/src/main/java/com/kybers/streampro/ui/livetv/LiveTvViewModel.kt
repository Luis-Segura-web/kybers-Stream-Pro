package com.kybers.streampro.ui.livetv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kybers.streampro.data.local.PreferencesManager
import com.kybers.streampro.domain.model.LiveStream
import com.kybers.streampro.domain.model.Category
import com.kybers.streampro.domain.usecase.GetLiveStreamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveTvViewModel @Inject constructor(
    private val getLiveStreamsUseCase: GetLiveStreamsUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiveTvUiState())
    val uiState: StateFlow<LiveTvUiState> = _uiState.asStateFlow()

    fun loadStreams() {
        if (_uiState.value.isLoading) return
        
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        viewModelScope.launch {
            try {
                val credentials = preferencesManager.loginCredentials.first()
                if (credentials.isLoggedIn) {
                    getLiveStreamsUseCase(credentials.username, credentials.password)
                        .onSuccess { streams ->
                            _uiState.value = _uiState.value.copy(
                                streams = streams,
                                filteredStreams = streams,
                                isLoading = false,
                                error = null
                            )
                        }
                        .onFailure { exception ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = exception.message ?: "Failed to load streams"
                            )
                        }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "No valid login credentials found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load streams"
                )
            }
        }
    }

    fun searchStreams(query: String) {
        val filtered = if (query.isBlank()) {
            _uiState.value.streams
        } else {
            _uiState.value.streams.filter { stream ->
                stream.name.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredStreams = filtered
        )
    }

    fun retry() {
        loadStreams()
    }
}

data class LiveTvUiState(
    val streams: List<LiveStream> = emptyList(),
    val filteredStreams: List<LiveStream> = emptyList(),
    val categories: List<Category> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)