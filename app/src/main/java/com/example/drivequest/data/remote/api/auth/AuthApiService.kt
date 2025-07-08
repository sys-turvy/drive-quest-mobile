package com.example.drivequest.data.remote.api.auth

import com.example.drivequest.data.remote.api.auth.model.LoginRequest
import com.example.drivequest.data.remote.api.auth.model.LoginResponse
import com.example.drivequest.data.remote.api.auth.model.RefreshTokenRequest
import com.example.drivequest.data.remote.api.auth.model.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<RefreshTokenResponse>
}