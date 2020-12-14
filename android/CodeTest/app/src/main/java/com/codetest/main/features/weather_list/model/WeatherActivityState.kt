package com.codetest.main.features.weather_list.model

import com.codetest.network.model.Location

sealed class WeatherActivityState {

    object Loading : WeatherActivityState()
    object Error : WeatherActivityState()
    class ShowLocationForecastList(val locations: List<Location>) : WeatherActivityState()

}
