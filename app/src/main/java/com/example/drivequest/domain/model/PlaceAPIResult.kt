package com.example.drivequest.domain.model

// 場所情報のレスポンスモデル
data class PlaceAPIResult(
    val placeId: String,
    val primaryText: String,
    val secondaryText: String
)