package com.codetest.main

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.codetest.R
import com.codetest.main.model.NewLocation
import com.codetest.main.model.Status
import kotlinx.android.synthetic.main.add_weather_forecast_location.*

class AddWeatherLocationForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_weather_forecast_location)

        val items = Status.values().map { it.description }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spn_status.adapter = adapter;

        btn_save_forecast_location.setOnClickListener {
            LocationHelper.postLocations(
                NewLocation(
                    et_forecast_location_name.text.toString(),
                    Status.values().first { it.description == spn_status.selectedItem }.name,
                    et_forecast_location_temprature.text.toString().toInt()
                )
            ) {
                if (it != null) {
                    finish()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle(resources.getString(R.string.error_title))
                        .setMessage(resources.getString(R.string.error_title))
                        .setPositiveButton(resources.getString(R.string.ok), { _, _ -> })
                        .create()
                        .show()
                }
            }
        }
    }
}