package com.example.drivequest.data.remote.datasource.auth

import com.example.drivequest.data.remote.api.auth.model.LoginResponse

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): Result<LoginResponse>
}