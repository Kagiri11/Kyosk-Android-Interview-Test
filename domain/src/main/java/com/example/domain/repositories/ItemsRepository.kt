package com.example.domain.repositories

import com.example.domain.models.Category
import com.example.domain.models.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    suspend fun fetchItems(): List<Item>

    suspend fun fetchCategories(): Flow<List<Category>>

    suspend fun fetchItemsByCategory(category: Category): List<Item>
}
