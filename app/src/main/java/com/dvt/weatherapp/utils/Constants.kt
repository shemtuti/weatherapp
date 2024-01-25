package com.dvt.weatherapp.utils

import android.Manifest
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val WEATHER_API_KEY = "d5ea53ab4a93cc8e42c3aa80b9d3e70a"
    val LOCATION_LAT = stringPreferencesKey("lat")
    val LOCATION_LON = stringPreferencesKey("lon")
    val PERMISSION_REQUEST_LOCATION = 100
    var permissionsLocation = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}