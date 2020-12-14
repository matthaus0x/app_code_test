package com.codetest.network.repository

import com.codetest.network.api.LocationApi
import com.codetest.network.model.Location
import com.codetest.network.model.LocationsResponse
import com.codetest.network.model.NewLocation
import com.codetest.network.util.KeyUtil
import io.reactivex.Maybe
import io.reactivex.Observable

class LocationRepository(
    private val locationApiService: LocationApi,
    private val keyUtil: KeyUtil) {

    fun getLocations() : Observable<LocationsResponse> {
        val apiKey = keyUtil.getKey()
        return locationApiService.getLocations(apiKey)
    }

    fun postLocations(location: NewLocation): Observable<Location> {
        val apiKey = keyUtil.getKey()
        return locationApiService.postLocation(apiKey, location)
    }

    fun deleteLocations(id: String): Maybe<Unit> {
        val apiKey = keyUtil.getKey()
        return locationApiService.deleteLocation(apiKey, id)
    }
}