package com.dvt.weatherapp.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.local.model.CurrentWeatherState
import com.dvt.weatherapp.data.local.model.ForecastDayWeatherState
import com.dvt.weatherapp.data.local.model.ForecastWeatherState
import com.dvt.weatherapp.data.remote.model.FetchResult
import com.dvt.weatherapp.repository.WeatherRepository
import com.dvt.weatherapp.utils.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {

    private val _stateCurrent = MutableStateFlow(CurrentWeatherState())
    val stateCurrent: StateFlow<CurrentWeatherState> = _stateCurrent

    private val _stateForecast = MutableStateFlow(ForecastWeatherState())
    val stateForecast: StateFlow<ForecastWeatherState> = _stateForecast

    /**
     * SAVE PREFERENCES
     */
    fun saveLat(lat: String) {
        viewModelScope.launch {
            appPreferences.setLat(lat)
        }
    }

    fun saveLon(lon: String) {
        viewModelScope.launch {
            appPreferences.setLon(lon)
        }
    }

    /**
     * TRIGGER FETCH CURRENT WEATHER DETAILS FROM SERVER
     */

    private val deviceLat = appPreferences.getLat()
    private val deviceLon = appPreferences.getLon()

    fun getRemoteCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentResult = repository.getRemoteCurrentWeather(lat, lon)) {
                is FetchResult.Loading -> {
                    _stateCurrent.value = stateCurrent.value.copy(
                        isRefreshingWeather = true
                    )
                }
                is FetchResult.Success -> {
                    _stateCurrent.value = stateCurrent.value.copy(
                        isSuccessWeather = true
                    )
                }
                is FetchResult.Error -> {
                    _stateCurrent.value = stateCurrent.value.copy(
                        isErrorWeather = true,
                        currentErrorMessage = "$TAG ${currentResult.exception.message}"
                    )
                }

            }
        }
    }

    /**
     * TRIGGER FETCH FORECAST WEATHER DETAILS FROM SERVER
     */
    fun getRemoteForecastWeather(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val currentResult = repository.getRemoteForecastWeather(lat, lon)) {
                is FetchResult.Loading -> {
                    _stateForecast.value = stateForecast.value.copy(
                        isRefreshingForecast = true
                    )
                }
                is FetchResult.Success -> {
                    _stateForecast.value = stateForecast.value.copy(
                        isSuccessForecast = true
                    )
                }
                is FetchResult.Error -> {
                    _stateForecast.value = stateForecast.value.copy(
                        isErrorForecast = true,
                        forecastErrorMessage = "$TAG ${currentResult.exception.message}"
                    )
                }

            }
        }
    }

    /**
     * GET CURRENT WEATHER DETAILS FROM ROOM
     */
    private val _currentWeather = repository.getCurrentWeather()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _stateCurrentWeather = MutableStateFlow(CurrentWeatherState())

    val stateCurrentWeather = combine(_stateCurrentWeather, _currentWeather) { stateCurrentWeather, currentWeather ->
        stateCurrentWeather.copy(
            weather = currentWeather
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), CurrentWeatherState())

    /**
     * GET FORECAST WEATHER DETAILS FROM ROOM
     */
    private val _forecastWeather = repository.getForecastWeather()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _stateForecastWeather = MutableStateFlow(ForecastWeatherState())

    val stateForecastWeather = combine(_stateForecastWeather, _forecastWeather) { stateForecastWeather, forecastWeather ->
        stateForecastWeather.copy(
            forecast = forecastWeather
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), ForecastWeatherState())

    /**
     * GET SELECTED DAY WEATHER DETAILS FROM ROOM
     */
    fun getForecastDayWeather(timestamp: String): Flow<List<ForecastDayWeatherState>> {
        return repository.getForecastDayWeather(timestamp)
    }

    private companion object {
        private const val TAG = "WeatherViewModel"
    }
}