package com.dvt.weatherapp

import android.app.Application
import com.dvt.weatherapp.di.databaseModule
import com.dvt.weatherapp.di.networkModule
import com.dvt.weatherapp.di.preferenceModule
import com.dvt.weatherapp.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)

            modules(
                databaseModule,
                networkModule,
                weatherModule,
                preferenceModule,
            )
        }
    }
}