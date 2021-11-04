package com.example.domain.repositories

import com.example.domain.models.Category
import com.example.domain.models.Item
import com.example.domain.models.Items

interface ItemsRepository {

    suspend fun fetchItems(): List<Item>

    suspend fun fetchCategories(): List<Category>

    suspend fun fetchItemsByCategory(category:Category): List<Item>
}
