package com.example.remote

import com.example.domain.models.Categories
import com.example.domain.models.Items
import com.example.remote.models.CategoriesResponseDto
import com.example.remote.models.ItemsResponseDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("items.json")
    suspend fun fetchItems(): Response<ItemsResponseDto>

    @GET("categories.json")
    suspend fun fetchCategories(): Response<CategoriesResponseDto>

}
