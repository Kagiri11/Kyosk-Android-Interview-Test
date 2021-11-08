package com.example.repository.sources

import androidx.lifecycle.MutableLiveData
import com.example.cache.AppDataBase
import com.example.cache.models.CategoryEntity
import com.example.cache.models.ItemEntity
import com.example.domain.models.Item
import com.example.domain.repositories.ItemsRepository
import com.example.remote.ApiService
import com.example.repository.mappers.toDomain
import com.example.repository.mappers.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ItemsRepositoryImpl(
    private val network: ApiService,
    private val database: AppDataBase
) : ItemsRepository {

    private val _categories = MutableLiveData<CategoryEntity>()
    private val _items = MutableLiveData<ItemEntity>()

    init {
        _categories.observeForever {
            CoroutineScope(Dispatchers.IO).launch {
                saveCategoryToDb(it)
            }
        }
        _items.observeForever {
            CoroutineScope(Dispatchers.IO).launch {
                saveItemToDb(it)
            }
        }
    }

    private suspend fun saveCategoryToDb(categoryEntity: CategoryEntity) =
        database.itemDao().insertCategory(categoryEntity)

    private suspend fun saveItemToDb(itemEntity: ItemEntity) = database.itemDao().insertItem(itemEntity)

    override suspend fun fetchItems(): Flow<List<Item>> {
        val isItemsTableNotEmpty = database.itemDao().countItemsTable() > 0

        return if (isItemsTableNotEmpty) {
            database.itemDao().getItems().map { it.map { it.toDomain() } }
        } else {
            val items = network.fetchItems()
            items.map { it.toEntity() }.forEach {
                _items.value = it
            }
            flowOf(items.map { it.toEntity().toDomain() })
        }
    }

    override suspend fun fetchCategories(): Flow<List<com.example.domain.models.Category>> {

        val isCategoriesTableEmpty = database.itemDao().countCategoriesTable() > 0

        return if (isCategoriesTableEmpty) {
            database.itemDao().getCategories().map { it.map { it.toDomain() } }
        } else {
            val categories = network.fetchCategories().categories
            categories.map { it.toEntity() }.forEach {
                _categories.value = it
            }
            database.itemDao().getCategories().map { it.map { it.toDomain() } }
        }
    }

    override suspend fun fetchItemsByCategory(category: String): Flow<List<Item>> {
        val dbCategoriesItems = database.itemDao().getItemsByCategory(category)
        return dbCategoriesItems.map { it.map { it.toDomain() } }
    }
}
