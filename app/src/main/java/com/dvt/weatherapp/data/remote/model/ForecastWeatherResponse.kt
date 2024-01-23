package com.dvt.weatherapp.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastWeatherResponse (
    @Json(name = "list")
    val list: List<ForecastList>,
)

@JsonClass(generateAdapter = true)
data class ForecastList(
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "main")
    val main: Main,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "rain")
    val rain: RainF?,
    @Json(name = "sys")
    val sys: SysF,
    @Json(name = "dt_txt")
    val dt_txt: String
)

@JsonClass(generateAdapter = true)
data class RainF(
    @Json(name = "3h")
    val rain1h: Double
)

@JsonClass(generateAdapter = true)
data class SysF(
    @Json(name = "pod")
    val pod: String
)