package com.dvt.weatherapp.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvt.weatherapp.data.local.model.FavouriteTable
import com.dvt.weatherapp.data.local.model.FavouriteWeatherState
import com.dvt.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val repository: WeatherRepository,
) : ViewModel() {

    /**
     * GET SELECTED FAVOURITE DETAILS FROM ROOM
     */

    private val _favourite = repository.getFavouriteWeather()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _stateFavourite = MutableStateFlow(FavouriteWeatherState())

    val stateFavourite = combine(_stateFavourite, _favourite) { stateFavourite, favouriteWeather ->
        stateFavourite.copy(
            favourite = favouriteWeather
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), FavouriteWeatherState())

    /**
     * SAVE FAVOURITE WEATHER
     */
    fun saveFavouriteWeather(favourite: FavouriteTable) {
        viewModelScope.launch {
            repository.saveFavouriteWeather(favourite)
        }
    }

    /**
     * DELETE FAVOURITE WEATHER
     */
    fun deleteFavourite(favourite: FavouriteTable) {
        viewModelScope.launch {
            repository.deleteFavourite(favourite)
        }
    }

    /**
     * DELETE FAVOURITE WEATHER BY LOCATION NAME
     */
    fun deleteFavouriteByName(name: String) {
        viewModelScope.launch {
            repository.deleteFavouriteByName(name)
        }
    }

    /**
     * CHECK WHETHER FAVOURITE LOCATION EXISTS
     */

    private val _isLocationNameExists = MutableStateFlow<Boolean?>(null)
    val isLocationNameExists: StateFlow<Boolean?>
        get() = _isLocationNameExists

    fun checkIsFavouriteStatus(name: String) {
        viewModelScope.launch {
            // repository.checkIsFavouriteStatus(name)
            _isLocationNameExists.value = repository.checkIsFavouriteStatus(name)

        }
    }
    private companion object {
        private const val TAG = "FavouriteViewModel"
    }
}
