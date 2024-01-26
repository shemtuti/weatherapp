package com.dvt.weatherapp.data.repository

import com.dvt.weatherapp.data.local.model.CurrentTable
import com.dvt.weatherapp.data.local.model.FavouriteTable
import com.dvt.weatherapp.data.local.model.ForecastDayWeatherState
import com.dvt.weatherapp.data.local.model.ForecastTable
import com.dvt.weatherapp.data.remote.model.FetchResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getRemoteCurrentWeather(lat: String, lon: String): FetchResult<Boolean>
    suspend fun getRemoteForecastWeather(lat: String, lon: String): FetchResult<Boolean>
    suspend fun saveFavouriteWeather(weather: FavouriteTable)

    fun getCurrentWeather(): Flow<CurrentTable>
    fun getForecastWeather(): Flow<List<ForecastTable>>
    fun getForecastDayWeather(timestamp: String): Flow<List<ForecastDayWeatherState>>
    fun getFavouriteWeather(): Flow<List<FavouriteTable>>

    suspend fun deleteFavourite(favourite: FavouriteTable)
    suspend fun deleteFavouriteByName(name: String)
    suspend fun checkIsFavouriteStatus(name: String): Boolean
}
