package com.dvt.weatherapp.di

import androidx.room.Room
import com.dvt.weatherapp.data.local.datasource.WeatherDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<WeatherDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            WeatherDatabase::class.java,
            "dvt_weather_app.db",
        ).build()
    }

    single { get<WeatherDatabase>().currentWeatherDao }

    single { get<WeatherDatabase>().forecastWeatherDao }

    single { get<WeatherDatabase>().favouriteWeatherDao }
}