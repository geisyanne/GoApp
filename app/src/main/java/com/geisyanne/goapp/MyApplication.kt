package com.geisyanne.goapp

import android.app.Application
import com.geisyanne.goapp.data.networkModule
import com.geisyanne.goapp.di.appModule
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                networkModule,
                appModule
            )
        }
    }

}