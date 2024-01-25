package com.dvt.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvt.weatherapp.data.local.model.ForecastDayWeatherState
import com.dvt.weatherapp.data.local.model.ForecastTable
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastWeatherDao {

    /**
     * Forecast Weather Queries
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastWeather(currentWeather: ForecastTable)

    // Return forecast for the next 5 days(beginning of day)
    @Query("SELECT * FROM forecastweathertable WHERE time = :timestamp ORDER BY dt LIMIT 5")
    fun getForecastWeather(timestamp: String = "09:00"): Flow<List<ForecastTable>>

    @Query("SELECT * FROM forecastweathertable WHERE date = :timestamp")
    fun getForecastDayWeather(timestamp: String): Flow<List<ForecastDayWeatherState>>


}