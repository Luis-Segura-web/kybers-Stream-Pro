package com.kybers.streampro.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val HOST_KEY = stringPreferencesKey("host")
        private val PORT_KEY = stringPreferencesKey("port")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    val loginCredentials: Flow<LoginCredentials> = dataStore.data.map { preferences ->
        LoginCredentials(
            host = preferences[HOST_KEY] ?: "",
            port = preferences[PORT_KEY] ?: "80",
            username = preferences[USERNAME_KEY] ?: "",
            password = preferences[PASSWORD_KEY] ?: "",
            isLoggedIn = preferences[IS_LOGGED_IN_KEY] ?: false
        )
    }

    suspend fun saveLoginCredentials(
        host: String,
        port: String,
        username: String,
        password: String
    ) {
        dataStore.edit { preferences ->
            preferences[HOST_KEY] = host
            preferences[PORT_KEY] = port
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun clearLoginCredentials() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

data class LoginCredentials(
    val host: String,
    val port: String,
    val username: String,
    val password: String,
    val isLoggedIn: Boolean
)