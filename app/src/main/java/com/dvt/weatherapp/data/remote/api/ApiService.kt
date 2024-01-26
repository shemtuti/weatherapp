package com.dvt.weatherapp.data.remote.api

import com.dvt.weatherapp.data.remote.model.CurrentWeatherResponse
import com.dvt.weatherapp.data.remote.model.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWeatherUsingLatLng(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") exclude: String,
        @Query("units") units: String = "metric",
    ): Response<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getForecastUsingLatLng(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") exclude: String,
        @Query("units") units: String = "metric",
    ): Response<ForecastWeatherResponse>
}
