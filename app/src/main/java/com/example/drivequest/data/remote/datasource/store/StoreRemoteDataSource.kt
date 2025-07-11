package com.example.drivequest.data.remote.datasource.store

import com.example.drivequest.data.remote.api.store.model.GetShopIconFrameResponse
import com.example.drivequest.data.remote.api.store.model.GetShopVoiceResponse

interface StoreRemoteDataSource {
    suspend fun getShopVoice(): Result<List<GetShopVoiceResponse>>
    suspend fun getShopIconFrame(): Result<List<GetShopIconFrameResponse>>
}