package com.example.domain.usecases

import com.example.domain.models.Category
import com.example.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FetchCategories(private val repository: ItemsRepository) {
    suspend operator fun invoke(): Flow<List<Category>> {
        val categories = repository.fetchCategories()
        return flowOf(categories)
    }
}
