package com.example.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val description: String
)
