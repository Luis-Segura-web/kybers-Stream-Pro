package com.kybers.streampro.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kybers.streampro.data.local.PreferencesManager
import com.kybers.streampro.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        // Load saved credentials
        viewModelScope.launch {
            preferencesManager.loginCredentials.collect { credentials ->
                _uiState.value = _uiState.value.copy(
                    host = credentials.host,
                    port = credentials.port,
                    username = credentials.username,
                    password = credentials.password
                )
            }
        }
    }

    fun updateHost(host: String) {
        _uiState.value = _uiState.value.copy(host = host)
    }

    fun updatePort(port: String) {
        _uiState.value = _uiState.value.copy(port = port)
    }

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.host.isBlank() || state.port.isBlank() || 
            state.username.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(
                error = "Please fill in all fields",
                isLoading = false
            )
            return
        }

        _uiState.value = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            loginUseCase(state.host, state.port, state.username, state.password)
                .onSuccess { (userInfo, serverInfo) ->
                    // Save credentials
                    preferencesManager.saveLoginCredentials(
                        state.host, state.port, state.username, state.password
                    )
                    _uiState.value = state.copy(isLoading = false, error = null)
                    onSuccess()
                }
                .onFailure { exception ->
                    _uiState.value = state.copy(
                        isLoading = false,
                        error = exception.message ?: "Login failed"
                    )
                }
        }
    }
}

data class LoginUiState(
    val host: String = "",
    val port: String = "80",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)