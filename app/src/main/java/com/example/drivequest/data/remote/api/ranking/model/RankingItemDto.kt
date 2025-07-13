package com.example.drivequest.data.remote.api.ranking.model

data class RankingItemDto(
    val rank: Int,
    val user: RankingUserDto,
    val distance: Double
)