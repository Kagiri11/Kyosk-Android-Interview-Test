package com.example.remote.models

data class ItemDto(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Int,
    val rating: RatingDto,
    val title: String
)
