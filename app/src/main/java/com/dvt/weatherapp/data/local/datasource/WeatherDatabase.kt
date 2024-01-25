package com.dvt.weatherapp.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvt.weatherapp.data.local.dao.CurrentWeatherDao
import com.dvt.weatherapp.data.local.dao.FavouriteWeatherDao
import com.dvt.weatherapp.data.local.dao.ForecastWeatherDao
import com.dvt.weatherapp.data.local.model.CurrentTable
import com.dvt.weatherapp.data.local.model.FavouriteTable
import com.dvt.weatherapp.data.local.model.ForecastTable

@Database(
    entities = [
        CurrentTable::class,
        ForecastTable::class,
        FavouriteTable::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val currentWeatherDao: CurrentWeatherDao
    abstract val forecastWeatherDao: ForecastWeatherDao
    abstract val favouriteWeatherDao: FavouriteWeatherDao
}