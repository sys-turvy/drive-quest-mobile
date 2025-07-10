package com.example.drivequest.presentation.store.mapper

import com.example.drivequest.domain.model.VoiceStyle
import com.example.drivequest.presentation.store.model.Product

fun VoiceStyle.toUiState(): Product {
    return Product(
        name = name,
        imgUrl = imgUrl,
        price = price,
        isOwned = isOwned
    )
}