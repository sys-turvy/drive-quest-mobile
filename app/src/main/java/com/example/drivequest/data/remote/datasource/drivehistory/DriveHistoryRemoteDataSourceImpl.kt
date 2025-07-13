package com.example.drivequest.data.remote.datasource.drivehistory

import com.example.drivequest.data.remote.api.drivehistory.DriveHistoryApiService
import com.example.drivequest.data.remote.api.drivehistory.model.DriveHistoryResponse
import retrofit2.HttpException
import javax.inject.Inject

class DriveHistoryRemoteDataSourceImpl @Inject constructor(
    private val apiService: DriveHistoryApiService
) : DriveHistoryRemoteDataSource {
    override suspend fun getAll(): Result<List<DriveHistoryResponse>> {
        return try {
            val response = apiService.getDriveHistories()
            if(response.isSuccessful) {
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