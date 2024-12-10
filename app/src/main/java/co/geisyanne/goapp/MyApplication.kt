package co.geisyanne.goapp

import android.app.Application
import co.geisyanne.goapp.data.networkModule
import co.geisyanne.goapp.di.appModule
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