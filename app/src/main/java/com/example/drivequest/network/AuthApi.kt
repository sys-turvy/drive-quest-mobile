package com.example.drivequest.network

import com.example.drivequest.model.LoginRequest
import com.example.drivequest.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}