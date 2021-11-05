package com.example.domain.models

data class Item(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Int,
    val rating: Rating,
    val title: String
)