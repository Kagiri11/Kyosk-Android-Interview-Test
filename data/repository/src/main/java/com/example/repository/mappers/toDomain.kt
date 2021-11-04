package com.example.repository.mappers

import com.example.domain.models.*
import com.example.remote.models.*

fun CategoryDto.toDomain(): Category {
    return Category(
        code = code,
        description = description
    )
}

fun CategoriesResponseDto.toDomain(): Categories{
    return Categories(
        categories = categories.map { it.toDomain() }
    )
}

fun RatingDto.toDomain(): Rating{
    return Rating(
        quantity = quantity,
        rate = rate
    )
}

fun ItemDto.toDomain(): Item {
    return Item(
        category = category,
        description = description,
        id = id,
        image = image,
        price = price,
        rating = rating.toDomain(),
        title=title
    )
}

fun ItemsResponseDto.toDomain(): Items {
    return Items(
        items = items.map { it.toDomain() }
    )
}