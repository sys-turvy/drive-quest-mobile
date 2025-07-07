package com.example.drivequest.token

import com.example.drivequest.data.remote.api.auth.AuthApiService
import com.example.drivequest.data.remote.api.auth.model.RefreshTokenRequest
import com.example.drivequest.domain.model.TokenPair
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshApi: Provider<AuthApiService> // リフレッシュ専用API
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { tokenManager.getRefreshToken() } ?: return null

        val authApiService = refreshApi.get()

        // リフレッシュ実行
        val refreshResponse = runBlocking {
            try {
                authApiService.refreshToken(RefreshTokenRequest(refreshToken))
            } catch (e: Exception) {
                null
            }
        }

        if (refreshResponse?.isSuccessful == true) {
            val newToken = refreshResponse.body()?.token ?: return null
            val newRefreshToken = refreshResponse.body()?.refreshToken ?: return null

            runBlocking {
                tokenManager.saveTokens(TokenPair(newToken, newRefreshToken))
            }

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        }

        return null // リフレッシュ失敗 → ログイン画面へ誘導など
    }
}