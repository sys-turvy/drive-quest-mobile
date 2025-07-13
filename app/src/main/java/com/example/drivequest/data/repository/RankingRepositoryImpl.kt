package com.example.drivequest.data.repository

import com.example.drivequest.data.remote.datasource.ranking.RankingRemoteDataSource
import com.example.drivequest.domain.mapper.toDomain
import com.example.drivequest.domain.model.Ranking
import com.example.drivequest.domain.repository.RankingRepository
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val rankingRemoteDataSource: RankingRemoteDataSource
) : RankingRepository {
    override suspend fun getWeeklyRanking(): Result<Ranking> {
        return rankingRemoteDataSource.getWeeklyRanking().map { response ->
            response.toDomain()
        }
    }

    override suspend fun getMonthlyRanking(): Result<Ranking> {
        return rankingRemoteDataSource.getMonthlyRanking().map { response ->
            response.toDomain()
        }
    }
}