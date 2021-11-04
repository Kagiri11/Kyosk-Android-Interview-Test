package com.example.repository.sources

import com.example.domain.models.Category
import com.example.domain.models.Item
import com.example.domain.repositories.ItemsRepository
import com.example.remote.ApiService

class ItemsRepositoryImpl(
    private val api: ApiService
) : ItemsRepository {

    override suspend fun fetchItems(): List<Item> {
        return if (api.fetchItems().isSuccessful) {
            val items = api.fetchItems()
            items.body()!!.items
        } else {
            emptyList<Item>()
        }
    }

    override suspend fun fetchCategories(): List<Category> {
        return if (api.fetchCategories().isSuccessful) {
            val categories = api.fetchCategories()
            categories.body()!!.categories
        } else {
            emptyList<Category>()
        }
    }

    override suspend fun fetchItemsByCategory(category: Category): List<Item> {
        return fetchItems().filter {
            it.category.description == category.description
        }
    }
}
