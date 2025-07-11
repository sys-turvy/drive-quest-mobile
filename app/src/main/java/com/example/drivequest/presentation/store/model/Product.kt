package com.example.drivequest.presentation.store.model

data class Product(
    val name: String,
    val imgUrl: String,
    val price: Int,
    val isOwned: Boolean
)
