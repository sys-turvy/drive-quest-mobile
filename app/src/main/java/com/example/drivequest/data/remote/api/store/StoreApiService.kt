package com.example.drivequest.data.remote.api.store

import com.example.drivequest.data.remote.api.store.model.GetShopIconFrameResponse
import com.example.drivequest.data.remote.api.store.model.GetShopVoiceResponse
import retrofit2.Response
import retrofit2.http.GET

interface StoreApiService {
    @GET("api/shop/icon-frames")
    suspend fun getShopIconFrame(): Response<List<GetShopIconFrameResponse>>

    @GET("api/shop/voice-styles")
    suspend fun getShopVoice(): Response<List<GetShopVoiceResponse>>
}