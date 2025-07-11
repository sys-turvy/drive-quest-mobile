package com.example.drivequest.domain.mapper

import com.example.drivequest.data.remote.api.store.model.GetShopIconFrameResponse
import com.example.drivequest.domain.model.IconFrame
import com.example.drivequest.domain.model.VoiceStyle

fun GetShopIconFrameResponse.toDomain(): IconFrame {
    return IconFrame(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl,
        price = this.price,
        isOwned = this.isOwned
    )
}