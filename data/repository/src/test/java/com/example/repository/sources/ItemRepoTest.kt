package com.example.repository.sources

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.cache.AppDataBase
import com.example.domain.models.Item
import com.example.domain.models.Rating
import com.example.remote.ApiService
import com.example.remote.models.ItemDto
import com.example.remote.models.ItemsResponseDto
import com.example.remote.models.RatingDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class ItemRepoTest {

    private lateinit var itemsRepositoryImpl: ItemsRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var db: AppDataBase

    val rating = RatingDto(12, 2f)
    val itemDto = ItemDto("dfgsdf", "desc", 12, "img", 230, rating, "test")
    val fetchResult = flowOf(listOf(itemDto))
    private lateinit var itemsResponseDto: ItemsResponseDto

    @Before
    fun setup() {
        apiService = mock()
        apiService.stub {
            onBlocking { apiService.fetchItems() }.doReturn(itemsResponseDto)
        }
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        itemsRepositoryImpl = ItemsRepositoryImpl(apiService, db)
    }

    @Test
    fun fetchItemsReturnsListItemFlow() = runBlocking {
        val items = itemsRepositoryImpl.fetchItems()
        assertThat(items).isEqualTo(fetchResult)
    }
}
