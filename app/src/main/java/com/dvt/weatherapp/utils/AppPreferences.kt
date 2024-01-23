package com.dvt.weatherapp.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.IOException


val Context.weatherDataStore: DataStore<Preferences> by preferencesDataStore(name = "weather_preferences")

class AppPreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun setLat(lat: String) {
        dataStore.edit { preferences ->
            preferences[Constants.LOCATION_LAT] = lat
        }
    }

    fun getLat(): String {
        return get(Constants.LOCATION_LAT) ?: ""
    }

    suspend fun setLon(lon: String) {
        dataStore.edit { preferences ->
            preferences[Constants.LOCATION_LON] = lon
        }
    }

    fun getLon(): String {
        return get(Constants.LOCATION_LON) ?: ""
    }

    private fun <T> get(key: Preferences.Key<T>): T? {
        return runBlocking {
            dataStore.data
                .safeCatch(key.name)
                .map { preferences -> preferences[key] }
                .firstOrNull()
        }
    }

    private fun Flow<Preferences>.safeCatch(name: String) = catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            Timber.tag(TAG).e(exception, "Error reading preferences %s", name)
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}

private const val TAG = "AppPreferences"