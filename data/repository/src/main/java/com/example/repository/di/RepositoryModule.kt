package com.example.repository.di

import com.example.domain.repositories.ItemsRepository
import com.example.repository.sources.ItemsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ItemsRepository> { ItemsRepositoryImpl(get()) }
}
