package com.example.drivequest.data.remote.datasource.drivehistory

import com.example.drivequest.data.remote.api.drivehistory.model.DriveHistoryResponse

interface DriveHistoryRemoteDataSource {
    suspend fun getAll(): Result<List<DriveHistoryResponse>>
}