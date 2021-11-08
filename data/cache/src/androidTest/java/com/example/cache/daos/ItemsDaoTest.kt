package com.example.cache.daos

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.cache.AppDataBase
import com.example.cache.models.CategoryEntity
import com.example.cache.models.ItemEntity
import com.example.cache.models.RatingEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ItemsDaoTest {

    private lateinit var database: AppDataBase
    private lateinit var dao: ItemsDbDao

    private val itemEntity = ItemEntity("item1", "", 11, "", 11, RatingEntity(11, 1f), "")
    private val itemEntity2 = ItemEntity("", "", 11, "", 11, RatingEntity(11, 1f), "")

    private val categoryEntity = CategoryEntity("", "")
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.itemDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertItem() = runBlocking {
        dao.insertItem(itemEntity)
        val itemCountInDb = dao.countItemsTable()
        assertThat(itemCountInDb).isGreaterThan(0)
    }

    @Test
    fun insertCategory() = runBlocking {
        dao.insertCategory(categoryEntity)
        val categoriesInDb = dao.countCategoriesTable()
        assertThat(categoriesInDb).isGreaterThan(0)
    }

    @Test
    fun getItems() = runBlocking {
        dao.insertItem(itemEntity)
        var itemsEntitiesFromDb = listOf<ItemEntity>()
        val items = dao.getItems()

        items.take(1).collect {
            itemsEntitiesFromDb = it
        }
        assertThat(itemsEntitiesFromDb).contains(itemEntity)

    }

    @Test
    fun getItemsByCategory()= runBlocking {
        dao.insertItem(itemEntity)
        assertThat(dao.getItems())
        var itemsEntitiesFromDb = listOf<ItemEntity>()
        val items = dao.getItemsByCategory("item1")
        items.take(1).collect {
            itemsEntitiesFromDb = it
        }
        assertThat(itemsEntitiesFromDb).contains(itemEntity)
    }

    @Test
    fun getCategories() = runBlocking {
        dao.insertCategory(categoryEntity)
        var categoryEntitiesFromDb = listOf<CategoryEntity>()
        val categories = dao.getCategories()
        categories.take(1).collect {
            categoryEntitiesFromDb = it
        }
        assertThat(categoryEntitiesFromDb.count()).isEqualTo(1)

    }
}
