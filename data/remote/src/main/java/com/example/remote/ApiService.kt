package com.example.remote

import com.example.domain.models.CategoriesResponse
import com.example.domain.models.ItemsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("items.json")
    suspend fun fetchItems(): Response<ItemsResponse>

    @GET("categories.json")
    suspend fun fetchCategories(): Response<CategoriesResponse>

}
