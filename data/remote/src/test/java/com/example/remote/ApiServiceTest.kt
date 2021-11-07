package com.example.remote

import com.example.remote.models.CategoriesResponseDto
import com.example.remote.models.ItemsResponseDto
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ApiServiceTest {

    // SUT
    private lateinit var apiService: ApiService


    private val categoriesResponseDto = CategoriesResponseDto(listOf())
    private val itemsResponseDto = ItemsResponseDto()

    @Before
    fun setUp() {
        apiService = mock()
        apiService.stub {
            onBlocking { this.fetchCategories() }.doReturn(categoriesResponseDto)
            onBlocking { this.fetchItems() }.doReturn(itemsResponseDto)
        }
    }

    @Test
    fun fetchItemsReturnsItemsResponseDto() {
        runBlocking {
            val response = apiService.fetchItems()
            assertThat(response).isEqualTo(itemsResponseDto)
        }
    }

    @Test
    fun fetchCategoriesReturnsCategoriesResponseDto(){
        runBlocking {
            val response = apiService.fetchCategories()
            assertThat(response).isEqualTo(categoriesResponseDto)
        }
    }
}
