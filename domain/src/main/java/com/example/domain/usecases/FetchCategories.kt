package com.example.domain.usecases

import com.example.domain.models.Category
import com.example.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.Flow

class FetchCategories(private val repository: ItemsRepository) {
    suspend operator fun invoke(): Flow<List<Category>> {
        return repository.fetchCategories()
    }
}
