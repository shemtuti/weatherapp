package com.dvt.weatherapp.di

import com.dvt.weatherapp.data.remote.api.ApiService
import com.dvt.weatherapp.repository.WeatherRepository
import com.dvt.weatherapp.repository.WeatherRepositoryImpl
import com.dvt.weatherapp.screens.viewmodels.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val weatherModule = module {

    factory<WeatherRepository> {
        WeatherRepositoryImpl(get(), get())
    }

    single {
        get<Retrofit>().create<ApiService>()
    }

    viewModel { WeatherViewModel(get(), get()) }
}