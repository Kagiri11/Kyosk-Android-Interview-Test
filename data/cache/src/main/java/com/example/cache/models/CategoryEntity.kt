package com.example.cache.models

import androidx.room.Entity

@Entity(tableName = "categories_table")
data class CategoryEntity(
    val code: String,
    val description: String
)
