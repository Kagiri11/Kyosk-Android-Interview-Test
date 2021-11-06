package com.example.kyosktest.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.HttpException
import com.example.domain.models.Category
import com.example.domain.usecases.FetchCategories
import com.example.domain.usecases.FetchItems
import com.example.domain.usecases.FetchItemsByCategory
import com.example.kyosktest.utils.Resource
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
        getItemsByCategory("INV4OTC")
    }

    private val _allItems: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val allItems: LiveData<Resource> get() = _allItems

    private val _nonFoodProducts: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val nonFoodProducts: LiveData<Resource> get() = _nonFoodProducts

    private val _foodProducts: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val foodProducts: LiveData<Resource> get() = _foodProducts

    private val _itemsByCategory: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val itemsByCategory: MutableLiveData<Resource> get() = _itemsByCategory

    private val _categories: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val categories: LiveData<Resource> get() = _categories

    var categoriesList = mutableListOf<Category>()

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
            _allItems.value =
                Resource.Error(e.localizedMessage ?: "Unable to connect to the internet")
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
            _categories.value =
                Resource.Error(e.localizedMessage ?: "Unable to connect to the internet")
        } catch (e: IOException) {
            _categories.value = Resource.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }

    fun getItemsByCategory(category: String) {
        viewModelScope.launch {

            fetchCategories.invoke().collect {
                categoriesList = it.toMutableList()
            }

            try {
//                val categoryCode = categoriesList.find { it.description == category }
                fetchItemsByCategory.invoke(category).collect { itemsByCategory ->
//                    val nonFood = itemsByCategory.filter { it.category == category }
//                    val food = itemsByCategory.filter { it.category != category }
                    _itemsByCategory.value = Resource.Success(itemsByCategory)
                }
            } catch (e: HttpException) {
                _itemsByCategory.value =
                    Resource.Error(e.localizedMessage ?: "Unable to connect to the internet")
            } catch (e: IOException) {
                _itemsByCategory.value =
                    Resource.Error(e.localizedMessage ?: "An unknown error occurred")
            }
        }
    }
}
