package com.example.drivequest.di

import com.example.drivequest.data.remote.api.drivehistory.DriveHistoryApiService
import com.example.drivequest.data.remote.datasource.drivehistory.DriveHistoryRemoteDataSource
import com.example.drivequest.data.remote.datasource.drivehistory.DriveHistoryRemoteDataSourceImpl
import com.example.drivequest.data.repository.DriveHistoryRepositoryImpl
import com.example.drivequest.domain.repository.DriveHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DriveHistoryModule {

    // DriveHistoryRepositoryの実装をバインドする
    @Binds
    @Singleton
    abstract fun bindDriveHistoryRepository(
        driveHistoryRepositoryImpl: DriveHistoryRepositoryImpl
    ): DriveHistoryRepository

    // DriveHistoryRemoteDataSourceの実装をバインドする
    // DriveHistoryRemoteDataSourceがインターフェースの場合のみ必要
    @Binds
    @Singleton
    abstract fun bindDriveHistoryRemoteDataSource(
        driveHistoryRemoteDataSourceImpl: DriveHistoryRemoteDataSourceImpl
    ): DriveHistoryRemoteDataSource

    companion object {
        // DriveHistoryApiServiceを提供する
        @Provides
        @Singleton
        fun provideDriveHistoryApiService(retrofit: Retrofit): DriveHistoryApiService {
            return retrofit.create(DriveHistoryApiService::class.java)
        }
    }
}