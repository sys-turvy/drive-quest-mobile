package com.example.drivequest.data.remote.api.drivehistory.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DriveHistoryResponse (
    val drivingHistoryId: String,
    val userId: String,
    val drivingMileage: Double,
    val durationTime: Int,
    val routeData: String?,
    val createdAt: String,
    val updatedAt: String
)