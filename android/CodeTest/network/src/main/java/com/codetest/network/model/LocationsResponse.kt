package com.codetest.network.model

import com.google.gson.annotations.SerializedName

data class LocationsResponse(@SerializedName("locations") val locations : List<Location>)
