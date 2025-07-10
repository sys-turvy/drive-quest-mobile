package com.example.drivequest.di

import com.example.drivequest.data.remote.api.store.StoreApiService
import com.example.drivequest.data.remote.datasource.store.StoreRemoteDataSource
import com.example.drivequest.data.remote.datasource.store.StoreRemoteDataSourceImpl
import com.example.drivequest.data.repository.StoreRepositoryImpl
import com.example.drivequest.domain.repository.StoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindStoreRepository(
        storeRepositoryImpl: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    @Singleton
    abstract fun bindStoreRemoteDataSource(
        storeRemoteDataSourceImpl: StoreRemoteDataSourceImpl
    ): StoreRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideStoreApiService(retrofit: Retrofit): StoreApiService {
            return retrofit.create(StoreApiService::class.java)
        }
    }
}