package com.example.drivequest.data.remote.datasource.auth

import com.example.drivequest.data.remote.api.auth.AuthApiService
import com.example.drivequest.data.remote.api.auth.model.LoginRequest
import com.example.drivequest.data.remote.api.auth.model.LoginResponse
import retrofit2.HttpException
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val apiService: AuthApiService
) : AuthRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val body = response.body() ?: return Result.failure(Exception("Empty body"))
                Result.success(body)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}