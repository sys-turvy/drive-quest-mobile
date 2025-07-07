package com.example.drivequest.token

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.drivequest.domain.model.TokenPair
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun saveTokens(tokenPair: TokenPair) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = tokenPair.token
            prefs[REFRESH_TOKEN] = tokenPair.refreshToken
        }
    }

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.first()[ACCESS_TOKEN]
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.first()[REFRESH_TOKEN]
    }

    suspend fun clearTokens() {
        context.dataStore.edit {
            it.remove(ACCESS_TOKEN)
            it.remove(REFRESH_TOKEN)
        }
    }
}