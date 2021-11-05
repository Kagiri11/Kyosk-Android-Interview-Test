package com.example.remote.models.new

data class IteItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Int,
    val rating: Rating,
    val title: String
)