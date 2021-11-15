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
import kotlinx.coroutines.delay
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

    private val _allItems: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val allItems: LiveData<Resource> get() = _allItems

    private val _nonFoodProducts: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val nonFoodProducts: LiveData<Resource> get() = _nonFoodProducts

    private val _foodProducts: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val foodProducts: LiveData<Resource> get() = _foodProducts

    private val _itemsByCategory: MutableLiveData<Resource> = MutableLiveData(Resource.Loading)
    val itemsByCategory: LiveData<Resource> get() = _itemsByCategory

    var categoriesList = listOf<Category>()

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
        fetchCategories.invoke().collect { categories ->
            categoriesList = categories
        }
    }

    fun getItemsByCategory(category: String) {
        if (category == "ALl") {
            getAllItems()
        } else {
            viewModelScope.apply {
                launch {
                    fetchCategories.invoke().collect {
                        categoriesList = it.toMutableList()
                        val categoryRequested = it.find { it.description == category }

                        println("This is the requested category: ${categoriesList.size}")
                    }
                }
                launch {
                    delay(1000)
                    try {
                        val cat =
                            categoriesList.find { it.description == category } ?: Category("", "")
                        fetchItemsByCategory.invoke(cat.code).collect { itemsByCategory ->
                            println("This is the list by requested category: ${itemsByCategory.size}")
                            val nonFood = itemsByCategory.filter { it.category == "INV4OTC" }
                            val food = itemsByCategory.filter { it.category != "INV4OTC" }
                            _foodProducts.value = Resource.Success(food)
                            _nonFoodProducts.value = Resource.Success(nonFood)
                            _itemsByCategory.value = Resource.Success(itemsByCategory)
                        }
                    } catch (e: HttpException) {
                        _itemsByCategory.value =
                            Resource.Error(
                                e.localizedMessage ?: "Unable to connect to the internet"
                            )
                    } catch (e: IOException) {
                        _itemsByCategory.value =
                            Resource.Error(e.localizedMessage ?: "An unknown error occurred")
                    }
                }
            }
        }
    }
}
