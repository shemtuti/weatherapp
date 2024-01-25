package com.dvt.weatherapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavouriteWeatherTable")
data class FavouriteTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val dt: Int,
    val lat: Double,
    val lon: Double,
    val currentWeather: String,
    val currentWeatherDesc: String,
    val tempNormal: Double,
    val tempMax: Double,
    val tempMin: Double,
    val locationName: String,
)