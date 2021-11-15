package com.example.cache.utils

import androidx.room.TypeConverter
import com.example.cache.models.CategoryEntity
import com.example.cache.models.RatingEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    @TypeConverter
    fun fromRating(ratingDto: RatingEntity): String {
        val type = object : TypeToken<RatingEntity>() {}.type
        return Gson().toJson(ratingDto, type)
    }

    @TypeConverter
    fun toRating(jString: String): RatingEntity {
        val type = object : TypeToken<RatingEntity>() {}.type
        return Gson().fromJson(jString, type)
    }

    @TypeConverter
    fun fromCategory(category: CategoryEntity): String {
        val type = object : TypeToken<CategoryEntity>() {}.type
        return Gson().toJson(category, type)
    }

    @TypeConverter
    fun toCategory(categoryString: String): CategoryEntity {
        val type = object : TypeToken<CategoryEntity>() {}.type
        return Gson().fromJson(categoryString, type)
    }
}
