package com.example.domain.repositories

import com.example.domain.models.Item

interface ItemsRepository {

    suspend fun fetchItems(): List<Item>
}
