package com.example.drivequest.domain.mapper

import com.example.drivequest.data.remote.api.store.model.GetShopVoiceResponse
import com.example.drivequest.domain.model.VoiceStyle

fun GetShopVoiceResponse.toDomain(): VoiceStyle {
    return VoiceStyle(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl,
        ttsVoiceId = this.ttsVoiceId,
        price = this.price,
        isOwned = this.isOwned
    )
}