package com.codetest.main

import com.codetest.main.api.LocationApiService
import com.codetest.main.model.Location
import com.codetest.main.model.NewLocation
import java.util.*

class LocationHelper {

    companion object {

        fun getLocations(callback: (List<Location>?) -> Unit) {
            val locations: ArrayList<Location> = arrayListOf()
            val apiKey = KeyUtil().getKey()
            LocationApiService.getApi().get(apiKey, "locations", {
                val list = it.get("locations").asJsonArray
                for (json in list) {
                    locations.add(Location.from(json.asJsonObject))
                }
                callback(locations)
            }, {
                callback(null)
            })
        }

        fun postLocations(location: NewLocation, callback: (Location?) -> Unit) {
            val apiKey = KeyUtil().getKey()
            LocationApiService.getApi().post(apiKey, "locations", location, {
                val location = Location.from(it.asJsonObject);
                callback(location)
            }, {
                callback(null)
            })
        }

        fun deleteLocations(id: String, callback: (Location?) -> Unit) {
            val locationUr = "locations/$id"
            val apiKey = KeyUtil().getKey()
            LocationApiService.getApi().delete(apiKey, locationUr, {
                val location = Location.from(it.asJsonObject);
                callback(location)
            }, {
                callback(null)
            })
        }
    }
}