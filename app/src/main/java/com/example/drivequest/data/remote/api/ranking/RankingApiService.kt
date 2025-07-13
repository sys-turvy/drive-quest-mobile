package com.example.drivequest.data.remote.api.ranking

import com.example.drivequest.data.remote.api.ranking.model.GetMonthlyRankingResponse
import com.example.drivequest.data.remote.api.ranking.model.GetWeeklyRankingResponse
import retrofit2.Response
import retrofit2.http.GET

interface RankingApiService {
    @GET("api/ranking/weekly-friends")
    suspend fun getWeeklyRanking(): Response<GetWeeklyRankingResponse>

    @GET("api/ranking/monthly-friends")
    suspend fun getMonthlyRanking(): Response<GetMonthlyRankingResponse>
}