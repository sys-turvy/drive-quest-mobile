package com.example.drivequest.data.remote.datasource.ranking

import com.example.drivequest.data.remote.api.ranking.model.GetMonthlyRankingResponse
import com.example.drivequest.data.remote.api.ranking.model.GetWeeklyRankingResponse

interface RankingRemoteDataSource {
    suspend fun getWeeklyRanking(): Result<GetWeeklyRankingResponse>
    suspend fun getMonthlyRanking(): Result<GetMonthlyRankingResponse>
}