package com.codetest.main.model

import com.google.gson.JsonObject

//Maybe we should receive this values from some endpoint :)
enum class Status(val value: Int, val description: String) {
    CLOUDY(0x2601, "Cloudy"),
    SUNNY(0x2600, "Sunny"),
    MOSTLY_SUNNY(0x1F324, "Mostly Sunny"),
    PARTLY_SUNNY(0x26C5, "Partly Sunny"),
    PARTLY_SUNNY_RAIN(0x1F326, "Party Sunny Rain"),
    THUNDER_CLOUD_AND_RAIN(0x26C8, "Thunder Cloud And Rain"),
    TORNADO(0x1F32A, "Tornado"),
    BARELY_SUNNY(0x1F325, "Barely Sunny"),
    LIGHTENING(0x1F329, "Lightening"),
    SNOW_CLOUD(0x1F328, "Snow Cloud"),
    RAINY(0x1F327, "Rainy");

    companion object {
        fun from(string: String): Status = values().first { it.name == string }
    }
}

class Location(
    val id: String?,
    val name: String?,
    val temperature: String?,
    val status: Status
) {

    companion object {
        fun from(jsonObject: JsonObject): Location {
            return Location(
                jsonObject.get("id").asString,
                jsonObject.get("name").asString,
                jsonObject.get("temperature").asString,
                Status.from(jsonObject.get("status").asString)
            )
        }
    }
}