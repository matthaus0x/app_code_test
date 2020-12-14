package com.codetest.main.features.weather_list.di

import com.codetest.main.features.weather_list.WeatherForecastViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherForecastModule = module {

    viewModel {
        WeatherForecastViewModel(get())
    }

}