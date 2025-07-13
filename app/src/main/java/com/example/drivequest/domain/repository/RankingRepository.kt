package com.example.drivequest.domain.repository

import com.example.drivequest.domain.model.Ranking

interface RankingRepository {
    suspend fun getWeeklyRanking(): Result<Ranking>
    suspend fun getMonthlyRanking(): Result<Ranking>
}