package com.example.repository.mappers

import com.example.cache.models.CategoryEntity
import com.example.cache.models.ItemEntity
import com.example.cache.models.RatingEntity
import com.example.domain.models.*
import com.example.remote.models.*

fun CategoryEntity.toDomain(): Category {
    return Category(
        code = code,
        description = description
    )
}

fun RatingEntity.toDomain(): Rating {
    return Rating(
        quantity = quantity,
        rate = rate
    )
}

fun ItemEntity.toDomain(): Item {
    return Item(
        category = category.toDomain(),
        description = description,
        id = id,
        image = image,
        price = price,
        rating = rating.toDomain(),
        title = title
    )
}
