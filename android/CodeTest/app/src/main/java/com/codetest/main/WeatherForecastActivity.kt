package com.codetest.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.codetest.R
import com.codetest.main.model.Location
import com.codetest.main.ui.LocationViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class WeatherForecastActivity : AppCompatActivity() {

    val locationHelper: LocationHelper by inject()

    private lateinit var adapter: ListAdapter

    private var locations: List<Location> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codetest.R.layout.activity_main)

        adapter = ListAdapter(onItemSelected = {locationId ->
            AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.remote_local_title))
                .setMessage(resources.getString(R.string.remote_local_description))
                .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                    locationHelper.deleteLocations(locationId) {}
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
        locationHelper.getLocations { response ->
            if (response == null) {
                showError()
            } else {
                locations = response
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showError() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(resources.getString(R.string.error_title))
            .setPositiveButton(resources.getString(R.string.ok), { _, _ -> })
            .create()
            .show()
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