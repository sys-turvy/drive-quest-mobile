package com.example.drivequest.domain.repository

import com.example.drivequest.domain.model.IconFrame
import com.example.drivequest.domain.model.VoiceStyle

interface StoreRepository {
    suspend fun getShopIconFrame(): Result<List<IconFrame>>
    suspend fun getShopVoiceStyle(): Result<List<VoiceStyle>>
}