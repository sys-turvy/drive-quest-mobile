package com.example.drivequest.data.remote.api.drivehistory

import com.example.drivequest.data.remote.api.drivehistory.model.DriveHistoryResponse
import retrofit2.Response

import retrofit2.http.GET

interface DriveHistoryApiService {
    @GET("api/driveHistory")
    suspend fun getDriveHistories(): Response<List<DriveHistoryResponse>>
}