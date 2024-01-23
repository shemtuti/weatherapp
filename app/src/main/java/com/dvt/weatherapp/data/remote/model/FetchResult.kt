package com.dvt.weatherapp.data.remote.model


sealed class FetchResult<out T : Any> {
    object Loading: FetchResult<Nothing>()
    data class Success<out T : Any>(val data: T) : FetchResult<T>()
    data class Error(val exception: Exception) : FetchResult<Nothing>()
}