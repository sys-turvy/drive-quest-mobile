package com.example.drivequest.domain.model

data class RankingEntry(
    val rank: Int,
    val user: RankingUser,
    val distanceKm: Double
)