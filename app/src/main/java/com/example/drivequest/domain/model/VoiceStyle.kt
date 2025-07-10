package com.example.drivequest.domain.model

data class VoiceStyle (
    val id: Int,
    val name: String,
    val imgUrl: String,
    val ttsVoiceId: String,
    val price: Int,
    val isOwned: Boolean
)