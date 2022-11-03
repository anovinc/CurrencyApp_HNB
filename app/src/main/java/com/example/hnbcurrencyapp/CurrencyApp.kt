package com.example.hnbcurrencyapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CurrencyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CurrencyApp)
            modules(com.example.hnbcurrencyapp.di.modules)
        }
        }
}