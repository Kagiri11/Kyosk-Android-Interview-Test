package com.example.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cache.models.CategoryEntity
import com.example.cache.models.ItemEntity

@Dao
interface ItemsDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemEntity: ItemEntity)

    @Query("SELECT * FROM items_table")
    suspend fun getItems(): List<ItemEntity>

    @Query("SELECT * FROM items_table WHERE category =:category")
    suspend fun getItemsByCategory(category: CategoryEntity): List<ItemEntity>
}
