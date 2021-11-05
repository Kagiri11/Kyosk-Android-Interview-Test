package com.example.kyosktest

import android.app.Application
import com.example.cache.di.cacheModule
import com.example.domain.di.domainModule
import com.example.kyosktest.di.presentationModule
import com.example.remote.di.networkModule
import com.example.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KyoskTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KyoskTestApp)
            modules(domainModule, presentationModule, repositoryModule, cacheModule, networkModule)
        }
    }
}
