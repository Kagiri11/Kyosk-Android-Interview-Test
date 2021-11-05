package com.example.kyosktest.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.example.domain.models.Category
import com.example.domain.usecases.FetchCategories
import com.example.domain.usecases.FetchItems
import com.example.domain.usecases.FetchItemsByCategory
import com.example.kyosktest.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(
    private val fetchItems: FetchItems,
    private val fetchCategories: FetchCategories,
    private val fetchItemsByCategory: FetchItemsByCategory
) : ViewModel() {

    init {
        getAllItems()
        getCategories()
    }

    private val _allItems: MutableStateFlow<Resource> = MutableStateFlow(Resource.Loading)
    val allItems: StateFlow<Resource> get() = _allItems

    private val _nonFoodProducts: MutableStateFlow<Resource> = MutableStateFlow(Resource.Loading)
    val nonFoodProducts: StateFlow<Resource> get() = _nonFoodProducts

    private val _foodProducts: MutableStateFlow<Resource> = MutableStateFlow(Resource.Loading)
    val foodProducts: StateFlow<Resource> get() = _foodProducts

    private val _itemsByCategory: MutableStateFlow<Resource> = MutableStateFlow(Resource.Loading)
    val itemsByCategory: StateFlow<Resource> get() = _itemsByCategory

    private val _categories: MutableStateFlow<Resource> = MutableStateFlow(Resource.Loading)
    val categories: StateFlow<Resource> get() = _categories

    fun getAllItems() = viewModelScope.launch {
        try {
            fetchItems.invoke().collect { items ->
                val nonFood = items.filter { it.category == "INV4OTC" }
                val food = items.filter { it.category != "INV4OTC" }
                _allItems.value = Resource.Success(items)
                _foodProducts.value = Resource.Success(food)
                _nonFoodProducts.value = Resource.Success(nonFood)
            }
        } catch (e: HttpException) {
            _allItems.value = Resource.Error(e.localizedMessage ?: "Unable to connect to the internet")
        } catch (e: IOException) {
            _allItems.value = Resource.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }

    fun getCategories() = viewModelScope.launch {
        try {
            fetchCategories.invoke().collect { categories ->
                _categories.value = Resource.Success(categories)
            }
        } catch (e: HttpException) {
            _categories.value = Resource.Error(e.localizedMessage ?: "Unable to connect to the internet")
        } catch (e: IOException) {
            _categories.value = Resource.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }

    fun getItemsByCategory(category: Category) {
        viewModelScope.launch {
            try {
                fetchItemsByCategory.invoke(category).collect { itemsByCategory ->
                    _itemsByCategory.value = Resource.Success(itemsByCategory)
                }
            } catch (e: HttpException) {
                _itemsByCategory.value = Resource.Error(e.localizedMessage ?: "Unable to connect to the internet")
            } catch (e: IOException) {
                _itemsByCategory.value = Resource.Error(e.localizedMessage ?: "An unknown error occurred")
            }
        }
    }
}
