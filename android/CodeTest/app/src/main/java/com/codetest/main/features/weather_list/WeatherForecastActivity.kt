package com.codetest.main.features.weather_list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.codetest.R
import com.codetest.main.features.add_weather.AddWeatherLocationForecastActivity
import com.codetest.network.model.Location
import com.codetest.network.repository.LocationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class WeatherForecastActivity : AppCompatActivity() {

    val locationHelper: LocationRepository by inject()

    private lateinit var adapter: ListAdapter

    private var locations: List<Location> = arrayListOf()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codetest.R.layout.activity_main)

        adapter = ListAdapter(onItemSelected = {locationId ->
            AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.remote_local_title))
                .setMessage(resources.getString(R.string.remote_local_description))
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    compositeDisposable.add(
                        locationHelper
                            .deleteLocations(locationId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                fetchLocations()
                            }, {
                                showError()
                            })
                    )
                }
                .create()
                .show()
        })

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        btn_add_forecast_location.setOnClickListener {
            Intent(this, AddWeatherLocationForecastActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchLocations()
    }

    private fun fetchLocations() {
        compositeDisposable.add(
            locationHelper
                .getLocations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ response ->
                    locations = response.locations
                    adapter.notifyDataSetChanged()
                }, {
                    showError()
                })
        )
    }

    private fun showError() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(resources.getString(R.string.error_message))
            .setPositiveButton(resources.getString(R.string.ok), { _, _ -> })
            .create()
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private inner class ListAdapter(val onItemSelected: (String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount(): Int {
            return locations.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return LocationViewHolder.create(parent, onItemSelected)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as? LocationViewHolder)?.setup(locations[position])
        }
    }
}