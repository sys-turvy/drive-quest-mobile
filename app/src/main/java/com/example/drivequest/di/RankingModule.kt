package com.example.drivequest.di

import com.example.drivequest.data.remote.api.ranking.RankingApiService
import com.example.drivequest.data.remote.datasource.ranking.RankingRemoteDataSource
import com.example.drivequest.data.remote.datasource.ranking.RankingRemoteDataSourceImpl
import com.example.drivequest.data.repository.RankingRepositoryImpl
import com.example.drivequest.domain.repository.RankingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RankingModule {

    @Binds
    @Singleton
    abstract fun bindRankingRepository(
        rankingRepositoryImpl: RankingRepositoryImpl
    ): RankingRepository

    @Binds
    @Singleton
    abstract fun bindRankingRemoteDataSource(
        rankingRemoteDataSourceImpl: RankingRemoteDataSourceImpl
    ): RankingRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideRankingApiService(retrofit: Retrofit): RankingApiService {
            return retrofit.create(RankingApiService::class.java)
        }
    }
}