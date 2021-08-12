package com.example.desafioframework

import android.app.Application
import com.example.desafioframework.data.di.DataModule
import com.example.desafioframework.domain.di.DomainModule
import com.example.desafioframework.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModule.load()
        DomainModule.load()
        PresentationModule.load()
    }
}