package com.dvt.weatherapp.data.local.model

data class CurrentWeatherState(
    val weather: CurrentTable? = null,
    val isSuccessWeather: Boolean = false,
    val isRefreshingWeather: Boolean = false,
    val isErrorWeather: Boolean = false,
    val currentErrorMessage: String = "",
)

data class ForecastWeatherState(
    val forecast: List<ForecastTable> = emptyList(),
    val isSuccessForecast: Boolean = false,
    val isRefreshingForecast: Boolean = false,
    val isErrorForecast: Boolean = false,
    val forecastErrorMessage: String = "",
)

data class ForecastDayWeatherState(
    val time: String? = "",
    val currentWeather: String? = "",
    val currentWeatherDesc: String? = "",
    val tempMax: Double? = 0.0,
    val tempMin: Double? = 0.0,
    val humidity: Int? = 0,
)