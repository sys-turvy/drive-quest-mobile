package com.example.drivequest.repository

import com.example.drivequest.model.LoginRequest
import com.example.drivequest.model.LoginResponse
import com.example.drivequest.network.ApiClient

class AuthRepository(
    private val tokenManager: TokenManager
) {
    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = ApiClient.authApi.login(LoginRequest(email, password))

            response.token.let { token ->
                tokenManager.saveToken(token)
            }

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getTokenFlow() = tokenManager.token

    suspend fun logout() {
        tokenManager.clearToken()
    }
}