package com.example.drivequest.data.remote.datasource.ranking

import com.example.drivequest.data.remote.api.ranking.RankingApiService
import com.example.drivequest.data.remote.api.ranking.model.GetMonthlyRankingResponse
import com.example.drivequest.data.remote.api.ranking.model.GetWeeklyRankingResponse
import retrofit2.HttpException
import javax.inject.Inject

class RankingRemoteDataSourceImpl @Inject constructor(
    private val apiService: RankingApiService
): RankingRemoteDataSource{
    override suspend fun getWeeklyRanking(): Result<GetWeeklyRankingResponse> {
        return try {
            val response = apiService.getWeeklyRanking()
            if(response.isSuccessful) {
                val body = response.body() ?: return Result.failure(Exception("Empty body"))
                Result.success(body)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMonthlyRanking(): Result<GetMonthlyRankingResponse> {
        return try {
            val response = apiService.getMonthlyRanking()
            if(response.isSuccessful) {
                val body = response.body() ?: return Result.failure(Exception("Empty body"))
                Result.success(body)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}