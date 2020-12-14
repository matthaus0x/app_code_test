package com.codetest.main.features.weather_list

import android.support.v7.widget.RecyclerView
import android.view.*
import com.codetest.R
import com.codetest.network.model.Location
import com.codetest.network.model.Status
import kotlinx.android.synthetic.main.location.view.*


class LocationViewHolder private constructor(itemView: View, val onItemLongClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup, onItemLongClick: (String) -> Unit): LocationViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.location, parent, false)
            return LocationViewHolder(view, onItemLongClick)
        }
    }

    fun setup(location: Location) {
        itemView.card.setCardBackgroundColor(getColor(location.status))
        itemView.name.text = location.name
        val weather = location.temperature + "°C " + String(Character.toChars(location.status.value))
        itemView.weatherInfo.text = weather

        location.id?.let { locationId ->
            itemView.setOnLongClickListener {
                onItemLongClick(locationId)
                true
            }
        }
    }

    private fun getColor(status: Status): Int {
        return when (status) {
            Status.SUNNY, Status.MOSTLY_SUNNY, Status.PARTLY_SUNNY, Status.PARTLY_SUNNY_RAIN, Status.BARELY_SUNNY
            -> itemView.resources.getColor(R.color.blue)
            Status.THUNDER_CLOUD_AND_RAIN, Status.TORNADO, Status.LIGHTENING -> itemView.resources.getColor(R.color.red)
            Status.CLOUDY, Status.SNOW_CLOUD, Status.RAINY -> itemView.resources.getColor(R.color.grey)

        }
    }
}
