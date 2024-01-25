package com.dvt.weatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvt.weatherapp.data.local.model.FavouriteTable
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteWeatherDao {

    /**
     * Current Favourite Queries
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteWeather(favouriteTable: FavouriteTable)

    @Query("SELECT * FROM favouriteweathertable ORDER BY id")
    fun getFavouriteWeather(): Flow<List<FavouriteTable>>

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteTable)

    @Query("DELETE FROM favouriteweathertable WHERE locationName = :name")
    suspend fun deleteFavouriteByName(name: String)

    @Query("SELECT EXISTS (SELECT 1 FROM favouriteweathertable WHERE locationName = :name)")
    suspend fun checkIsFavouriteStatus(name: String): Boolean

}