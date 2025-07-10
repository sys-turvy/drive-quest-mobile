package com.example.drivequest.data.remote.datasource.store

import com.example.drivequest.data.remote.api.store.StoreApiService
import com.example.drivequest.data.remote.api.store.model.GetShopIconFrameResponse
import com.example.drivequest.data.remote.api.store.model.GetShopVoiceResponse
import retrofit2.HttpException
import javax.inject.Inject

class StoreRemoteDataSourceImpl @Inject constructor(
    private val apiService: StoreApiService
) : StoreRemoteDataSource {
    override suspend fun getShopVoice(): Result<List<GetShopVoiceResponse>> {
        return try {
            val response = apiService.getShopVoice()
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

    override suspend fun getShopIconFrame(): Result<List<GetShopIconFrameResponse>> {
        return try {
            val response = apiService.getShopIconFrame()
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