package com.luckymollo.dogpark.client.application

import android.app.Application
import com.luckymollo.dogpark.client.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(appModules)
        }
    }
}
