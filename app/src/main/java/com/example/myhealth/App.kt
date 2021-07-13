package com.example.myhealth

import android.app.Application
import com.example.myhealth.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    companion object {
        lateinit var instance: App
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    appModule,
                    navigation,
                    splashActivity,
                    healthFragmentModule,
                    viewModelModule,
                    mainActivity
                )
            )
        }
    }
}