package com.kybers.streampro.ui.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kybers.streampro.data.local.PreferencesManager
import com.kybers.streampro.domain.model.LiveStream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _credentialsState = MutableStateFlow<NavigationState>(NavigationState.Loading)
    val credentialsState: StateFlow<NavigationState> = _credentialsState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesManager.loginCredentials.collect { credentials ->
                _credentialsState.value = NavigationState.Success(credentials)
            }
        }
    }

    suspend fun getStreamUrl(streamId: Int): String {
        val credentials = preferencesManager.loginCredentials.first()
        return "http://${credentials.host}:${credentials.port}/live/${credentials.username}/${credentials.password}/$streamId.m3u8"
    }
}

sealed class NavigationState {
    object Loading : NavigationState()
    data class Success(val credentials: com.kybers.streampro.data.local.LoginCredentials) : NavigationState()
}