package com.example.drivequest.data.repository

import com.example.drivequest.data.remote.datasource.drivehistory.DriveHistoryRemoteDataSource
import com.example.drivequest.domain.mapper.toDomain
import com.example.drivequest.domain.model.DriveHistory
import com.example.drivequest.domain.repository.DriveHistoryRepository
import javax.inject.Inject

class DriveHistoryRepositoryImpl @Inject constructor(
    private val driveHistoryRemoteDataSource: DriveHistoryRemoteDataSource
) : DriveHistoryRepository {
    override suspend fun getDriveHistories(): Result<List<DriveHistory>> {
        return driveHistoryRemoteDataSource.getAll().map { responseList ->
            responseList.map { it.toDomain() } }
    }
}