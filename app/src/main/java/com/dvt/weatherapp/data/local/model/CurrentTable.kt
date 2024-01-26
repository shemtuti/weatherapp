package com.dvt.weatherapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CurrentWeatherTable")
data class CurrentTable(
    val dt: Int,
    val lat: Double,
    val lon: Double,
    val currentWeather: String,
    val currentWeatherDesc: String,
    val tempNormal: Double,
    val tempMax: Double,
    val tempMin: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    @PrimaryKey
    val locationName: String,
)
