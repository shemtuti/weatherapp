package com.dvt.weatherapp.di

import com.dvt.weatherapp.utils.AppPreferences
import com.dvt.weatherapp.utils.weatherDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    single { androidContext().weatherDataStore }
    single { AppPreferences(get()) }
}
