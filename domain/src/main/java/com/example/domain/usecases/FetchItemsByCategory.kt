package com.example.domain.usecases

import com.example.domain.models.Category
import com.example.domain.models.Item
import com.example.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FetchItemsByCategory(private val repository: ItemsRepository) {
    suspend operator fun invoke(category: Category): Flow<List<Item>> {
        val items = repository.fetchItemsByCategory(category)
        return flowOf(items)
    }
}
