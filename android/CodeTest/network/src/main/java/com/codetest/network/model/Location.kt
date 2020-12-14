package com.codetest.network.model

import com.google.gson.JsonObject

data class Location(
    val id: String?,
    val name: String?,
    val temperature: String?,
    val status: Status
)
