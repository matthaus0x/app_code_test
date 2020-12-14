package com.codetest.main.features.weather_list

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.codetest.network.model.Location

class WeatherForecastAdapter(
    val locations: List<Location>,
    val onItemSelected: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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