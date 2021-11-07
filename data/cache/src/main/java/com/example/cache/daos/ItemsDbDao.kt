package com.example.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cache.models.CategoryEntity
import com.example.cache.models.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemEntity: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM items_table")
    fun getItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items_table WHERE category =:category")
    fun getItemsByCategory(category: String): Flow<List<ItemEntity>>

    @Query("SELECT COUNT (*) FROM categories_table")
    suspend fun isCategoriesTableEmpty(): Int

    @Query("SELECT COUNT (*) FROM items_table")
    suspend fun countItemsTable(): Int

    @Query("SELECT * FROM categories_table")
    fun getCategories(): Flow<List<CategoryEntity>>
}
