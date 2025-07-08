package com.example.drivequest.domain.model

data class DriveHistory (
    val id: String,
    val distanceKm: Double,
    val durationMinutes: Int,
    val timestamp: String
)