package com.dvt.weatherapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ForecastWeatherTable")
data class ForecastTable(
    @PrimaryKey
    val dt: String = "",
    val date: String = "",
    val day: String = "",
    val time: String = "",
    val currentWeather: String?,
    val currentWeatherDesc: String?,
    val tempNormal: Double?,
    val tempMax: Double?,
    val tempMin: Double?,
    val humidity: Int?,
    val partOfTheDay: String?,
    val timeForecasted: String?,
)
