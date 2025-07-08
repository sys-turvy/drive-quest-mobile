package com.example.drivequest.data.repository

import com.example.drivequest.data.remote.api.auth.model.LoginResponse
import com.example.drivequest.data.remote.datasource.auth.AuthRemoteDataSource
import com.example.drivequest.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return authRemoteDataSource.login(email, password)
    }
}