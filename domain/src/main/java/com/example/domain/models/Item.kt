package com.example.domain.models

data class Item(
    val category: Category,
    val description: String,
    val id: Int,
    val image: String,
    val price: Int,
    val rating: Rating,
    val title: String
)