package com.codetest.main.features.add_weather

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.codetest.R
import com.codetest.network.model.NewLocation
import com.codetest.network.model.Status
import com.codetest.network.repository.LocationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_weather_forecast_location.*
import org.koin.android.ext.android.inject

class AddWeatherLocationForecastActivity : AppCompatActivity() {

    private val locationHelper: LocationRepository by inject()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_weather_forecast_location)

        val items = Status.values().map { it.description }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        spn_status.adapter = adapter;

        btn_save_forecast_location.setOnClickListener {
            compositeDisposable.add(
                locationHelper.postLocations(
                    NewLocation(
                        et_forecast_location_name.text.toString(),
                        Status.values().first { it.description == spn_status.selectedItem },
                        et_forecast_location_temprature.text.toString().toInt()
                    ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        finish()
                    }, {
                        AlertDialog.Builder(this)
                            .setTitle(resources.getString(R.string.error_title))
                            .setMessage(resources.getString(R.string.error_message))
                            .setPositiveButton(resources.getString(R.string.ok), { _, _ -> })
                            .create()
                            .show()
                    })
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}