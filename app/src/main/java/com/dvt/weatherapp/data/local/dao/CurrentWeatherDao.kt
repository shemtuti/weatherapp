package com.dvt.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvt.weatherapp.data.local.model.CurrentTable
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {

    /**
     * Current Weather Queries
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentTable)

    @Query("SELECT * FROM currentweathertable ORDER BY dt DESC LIMIT 1")
    fun getCurrentWeather(): Flow<CurrentTable>
}
