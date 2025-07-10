package com.example.drivequest.data.repository

import com.example.drivequest.data.remote.datasource.store.StoreRemoteDataSource
import com.example.drivequest.domain.mapper.toDomain
import com.example.drivequest.domain.model.IconFrame
import com.example.drivequest.domain.model.VoiceStyle
import com.example.drivequest.domain.repository.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeRemoteDataSource: StoreRemoteDataSource
) : StoreRepository {
    override suspend fun getShopIconFrame(): Result<List<IconFrame>> {
        return storeRemoteDataSource.getShopIconFrame().map { responseList ->
            responseList.map { it.toDomain() }
        }
    }

    override suspend fun getShopVoiceStyle(): Result<List<VoiceStyle>> {
        return storeRemoteDataSource.getShopVoice().map { responseList ->
            responseList.map { it.toDomain() }
        }
    }
}