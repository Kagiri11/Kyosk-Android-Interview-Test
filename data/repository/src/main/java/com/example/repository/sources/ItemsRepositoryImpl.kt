package com.example.repository.sources

import androidx.lifecycle.MutableLiveData
import com.example.cache.AppDataBase
import com.example.cache.models.CategoryEntity
import com.example.domain.models.Item
import com.example.domain.repositories.ItemsRepository
import com.example.remote.ApiService
import com.example.repository.mappers.toDomain
import com.example.repository.mappers.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ItemsRepositoryImpl(
    private val network: ApiService,
    private val database: AppDataBase
) : ItemsRepository {

    private val _categories = MutableLiveData<CategoryEntity>()

    init {
        _categories.observeForever {
            CoroutineScope(Dispatchers.IO).launch {
                saveCategoryToDb(it)
            }
        }
    }

    private suspend fun saveCategoryToDb(categoryEntity: CategoryEntity) =
        database.itemDao().insertCategory(categoryEntity)

    override suspend fun fetchItems(): List<Item> {
        return if (network.fetchItems().isSuccessful) {
            val items = network.fetchItems()
            items.body()!!.items.map { it.toEntity().toDomain() }
        } else {
            emptyList<Item>()
        }
    }

    override suspend fun fetchCategories(): Flow<List<com.example.domain.models.Category>> {

        val isCategoriesTableEmpty = database.itemDao().isCategoriesTableEmpty() > 0

        return if (isCategoriesTableEmpty) {
            database.itemDao().getCategories().map { it.map { it.toDomain() } }
        } else {
            val categories = network.fetchCategories().body()!!.categories
            categories.map { it.toEntity() }.forEach {
                _categories.value = it
            }
            database.itemDao().getCategories().map { it.map { it.toDomain() } }
        }
    }

    override suspend fun fetchItemsByCategory(category: com.example.domain.models.Category): List<Item> {
        return fetchItems().filter {
            it.category.description == category.description
        }
    }
}
