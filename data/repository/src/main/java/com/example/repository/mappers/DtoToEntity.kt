package com.example.repository.mappers

import com.example.cache.models.CategoryEntity
import com.example.cache.models.ItemEntity
import com.example.cache.models.RatingEntity
import com.example.domain.models.*
import com.example.remote.models.*

fun CategoryDto.toEntity(): CategoryEntity {
    return CategoryEntity(
        code = code,
        description = description
    )
}

fun RatingDto.toEntity(): RatingEntity {
    return RatingEntity(
        quantity = quantity,
        rate = rate
    )
}

fun ItemDto.toEntity(): ItemEntity {
    return ItemEntity(
        category = category.toEntity(),
        description = description,
        id = id,
        image = image,
        price = price,
        rating = rating.toEntity(),
        title = title
    )
}
