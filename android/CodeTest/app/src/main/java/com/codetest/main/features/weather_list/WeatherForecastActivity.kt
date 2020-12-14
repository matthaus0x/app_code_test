package com.codetest.main.features.weather_list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.codetest.R
import com.codetest.main.features.add_weather.AddWeatherLocationForecastActivity
import com.codetest.main.features.weather_list.model.WeatherActivityState
import com.codetest.network.model.Location
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class WeatherForecastActivity : AppCompatActivity() {

    private val weatherForecastViewModel : WeatherForecastViewModel by viewModel()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setObservers()

        btn_add_forecast_location.setOnClickListener {
            Intent(this, AddWeatherLocationForecastActivity::class.java).also {
                startActivity(it)
            }
        }

        weatherForecastViewModel.fetchAllLocations()
    }

    private fun updateForecastLocationList(locations: List<Location>) {
        val listAdapter = WeatherForecastAdapter(locations, onItemSelected = { locationId ->
            showDeleteConfirmation(locationId)
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@WeatherForecastActivity, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }
        listAdapter.notifyDataSetChanged()
    }

    private fun setObservers() {
        weatherForecastViewModel.state.observe(this) {
            when (it) {
                is WeatherActivityState.Error -> showError()
                is WeatherActivityState.ShowLocationForecastList -> {
                    updateForecastLocationList(it.locations)
                }
                is WeatherActivityState.Loading -> {
                }
            }
        }
    }

    private fun showError() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(resources.getString(R.string.error_message))
            .setPositiveButton(resources.getString(R.string.ok), { _, _ -> })
            .create()
            .show()
    }

    private fun showDeleteConfirmation(locationId: String) {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.remote_local_title))
            .setMessage(resources.getString(R.string.remote_local_description))
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                weatherForecastViewModel.removeLocationById(locationId)
            }
            .create()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}