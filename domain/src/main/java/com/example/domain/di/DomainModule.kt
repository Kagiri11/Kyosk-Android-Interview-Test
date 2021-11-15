package com.example.domain.di

import com.example.domain.usecases.FetchCategories
import com.example.domain.usecases.FetchItems
import com.example.domain.usecases.FetchItemsByCategory
import org.koin.dsl.module

val domainModule = module {
    single { FetchCategories(get()) }
    single { FetchItemsByCategory(get()) }
    single { FetchItems(get()) }
}
