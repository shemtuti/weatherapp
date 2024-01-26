package com.dvt.weatherapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "base")
    val base: String,
    @Json(name = "main")
    val main: Main,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "rain")
    val rain: Rain?,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "name")
    val name: String,
)

@JsonClass(generateAdapter = true)
data class Coord(
    @Json(name = "lon")
    val lon: Double,
    @Json(name = "lat")
    val lat: Double,
)

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String,
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "feels_like")
    val feels_like: Double,
    @Json(name = "temp_min")
    val temp_min: Double,
    @Json(name = "temp_max")
    val temp_max: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "humidity")
    val humidity: Int,
)

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: Double,
)

@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "1h")
    val rain1h: Double,
)

@JsonClass(generateAdapter = true)
data class Clouds(
    @Json(name = "all")
    val all: Int,
)

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "country")
    val country: String?,
    @Json(name = "sunrise")
    val sunrise: String?,
    @Json(name = "sunset")
    val sunset: String?,
)
