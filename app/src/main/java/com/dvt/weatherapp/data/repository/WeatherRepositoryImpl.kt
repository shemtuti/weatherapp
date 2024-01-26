package com.dvt.weatherapp.data.repository

import com.dvt.weatherapp.data.local.datasource.WeatherDatabase
import com.dvt.weatherapp.data.local.model.CurrentTable
import com.dvt.weatherapp.data.local.model.FavouriteTable
import com.dvt.weatherapp.data.local.model.ForecastDayWeatherState
import com.dvt.weatherapp.data.local.model.ForecastTable
import com.dvt.weatherapp.data.remote.api.ApiService
import com.dvt.weatherapp.data.remote.model.FetchResult
import com.dvt.weatherapp.utils.Constants
import com.dvt.weatherapp.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException

internal class WeatherRepositoryImpl(
    private val database: WeatherDatabase,
    private val apiService: ApiService,
): WeatherRepository {
    override suspend fun getRemoteCurrentWeather(lat: String, lon: String): FetchResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                FetchResult.Loading
                val currentWeatherResult  =
                    apiService.getCurrentWeatherUsingLatLng(lat, lon, Constants.WEATHER_API_KEY)
                if(currentWeatherResult.isSuccessful) {
                    currentWeatherResult.body()?.apply {
                        val currentWeather = CurrentTable(
                            dt,
                            coord.lat,
                            coord.lon,
                            weather.get(0).main,
                            weather.get(0).description,
                            main.temp,
                            main.temp_min,
                            main.temp_max,
                            main.humidity,
                            main.pressure,
                            wind.speed,
                            name,
                        )
                        database.currentWeatherDao.insertCurrentWeather(currentWeather)
                    }
                    FetchResult.Success(true)
                } else {
                    FetchResult.Success(false)
                    FetchResult.Error(Exception(currentWeatherResult.errorBody().toString()))
                }
            } catch (e: IOException) {
                FetchResult.Error(Exception("Error fetching current weather data!"))
                e.printStackTrace()
                Timber.e(e)
            }
            FetchResult.Success(false)
        }
    }

    override fun getCurrentWeather(): Flow<CurrentTable> {
        return database.currentWeatherDao.getCurrentWeather()
    }

    override suspend fun getRemoteForecastWeather(lat: String, lon: String): FetchResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                FetchResult.Loading
                val forecastResult =
                    apiService.getForecastUsingLatLng(lat, lon, Constants.WEATHER_API_KEY)
                if(forecastResult.isSuccessful) {
                    forecastResult.body()?.list?.forEach { item ->
                        val forecast = ForecastTable(
                            Util.convertToDateTime(item.dt),
                            Util.convertToDate(item.dt),
                            Util.convertToDayOfWeek(item.dt),
                            Util.convertHourOfDay(item.dt),
                            item.weather.get(0).main,
                            item.weather.get(0).description,
                            item.main.temp,
                            item.main.temp_min,
                            item.main.temp_max,
                            item.main.humidity,
                            item.sys.pod,
                            item.dt_txt,
                        )
                        database.forecastWeatherDao.insertForecastWeather(forecast)
                    }
                    FetchResult.Success(true)
                } else {
                    FetchResult.Success(false)
                    FetchResult.Error(Exception(forecastResult.errorBody().toString()))
                }
            } catch (e: IOException) {
                FetchResult.Error(Exception("Error fetching forecast data!"))
                e.printStackTrace()
                Timber.e(e)
            }
            FetchResult.Success(false)
        }
    }

    override fun getForecastWeather(): Flow<List<ForecastTable>> {
        return database.forecastWeatherDao.getForecastWeather()
    }

    override fun getForecastDayWeather(timestamp: String): Flow<List<ForecastDayWeatherState>> {
        return database.forecastWeatherDao.getForecastDayWeather(timestamp)
    }

    override fun getFavouriteWeather(): Flow<List<FavouriteTable>> {
        return database.favouriteWeatherDao.getFavouriteWeather()
    }

    override suspend fun saveFavouriteWeather(weather: FavouriteTable) {
        database.favouriteWeatherDao.insertFavouriteWeather(weather)
    }

    override suspend fun deleteFavourite(favourite: FavouriteTable) {
        database.favouriteWeatherDao.deleteFavourite(favourite)
    }
    override suspend fun deleteFavouriteByName(name: String) {
        database.favouriteWeatherDao.deleteFavouriteByName(name)
    }
    override suspend fun checkIsFavouriteStatus(name: String) : Boolean {
        return database.favouriteWeatherDao.checkIsFavouriteStatus(name)
    }



}
