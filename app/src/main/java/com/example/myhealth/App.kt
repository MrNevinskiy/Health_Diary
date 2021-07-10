package com.example.myhealth

import android.app.Application
import com.example.myhealth.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    appModule,
                    healthFragmentModule,
                    enterDataFragmentModule
                )
            )
        }
    }
}