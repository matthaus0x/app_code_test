package com.codetest.network.api

import com.codetest.network.model.Location
import com.codetest.network.model.LocationsResponse
import com.codetest.network.model.NewLocation
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.*

interface LocationApi {
    @GET("/locations")
    fun getLocations(@Header("X-Api-Key") apiKey: String): Observable<LocationsResponse>
    @POST("/locations")
    fun postLocation(@Header("X-Api-Key") apiKey: String, @Body location: NewLocation): Observable<Location>
    @DELETE("/locations/{id}")
    fun deleteLocation(@Header("X-Api-Key") apiKey: String, @Path("id") id: String): Maybe<Unit>
}