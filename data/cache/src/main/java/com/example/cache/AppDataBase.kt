package com.example.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cache.daos.ItemsDbDao
import com.example.cache.models.ItemEntity
import com.example.cache.utils.Converters

@Database(entities = [ItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemsDbDao
}
