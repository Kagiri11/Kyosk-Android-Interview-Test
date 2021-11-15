package com.example.domain.usecases

import com.example.domain.models.Item
import com.example.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.Flow

class FetchItems(private val repository: ItemsRepository) {
    suspend operator fun invoke(): Flow<List<Item>> {
        val items = repository.fetchItems()
        return items
    }
}
