package com.codetest.main.features.weather_list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.codetest.main.features.weather_list.model.WeatherActivityState
import com.codetest.network.repository.LocationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherForecastViewModel(val locationRepository: LocationRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = MutableLiveData<WeatherActivityState>()

    val state : LiveData<WeatherActivityState> = _state

    fun fetchAllLocations() {
        _state.postValue(WeatherActivityState.Loading)

        compositeDisposable.add(
            locationRepository
                .getLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ response ->
                    _state.postValue(WeatherActivityState.ShowLocationForecastList(
                        response.locations
                    ))
                }, {
                    _state.postValue(WeatherActivityState.Error)
                }))
    }

    fun removeLocationById(id: String) {
        _state.postValue(WeatherActivityState.Loading)
        compositeDisposable.add(
            locationRepository
                .deleteLocations(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fetchAllLocations()
                }, {
                    _state.postValue(WeatherActivityState.Error)
                })
        )
    }

}