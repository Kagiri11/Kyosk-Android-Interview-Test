package com.example.remote

import com.example.remote.models.CategoriesResponseDto
import com.example.remote.models.ItemsResponseDto


import retrofit2.http.GET

interface ApiService {

    @GET("items.json")
    suspend fun fetchItems(): ItemsResponseDto

    @GET("categories.json")
    suspend fun fetchCategories(): CategoriesResponseDto

}
