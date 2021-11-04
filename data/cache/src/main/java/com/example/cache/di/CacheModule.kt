package com.example.cache.di

import androidx.room.Room
import com.example.cache.AppDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.dsl.single

val cacheModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDataBase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<AppDataBase>().itemDao() }
}
