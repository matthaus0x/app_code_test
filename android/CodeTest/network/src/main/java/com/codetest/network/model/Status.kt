package com.codetest.network.model

import com.google.gson.annotations.SerializedName

//Maybe we should receive this values from some endpoint :)
enum class Status(val value: Int, val description: String) {
    @SerializedName("CLOUDY")
    CLOUDY(0x2601, "Cloudy"),
    @SerializedName("SUNNY")
    SUNNY(0x2600, "Sunny"),
    @SerializedName("MOSTLY_SUNNY")
    MOSTLY_SUNNY(0x1F324, "Mostly Sunny"),
    @SerializedName("PARTLY_SUNNY")
    PARTLY_SUNNY(0x26C5, "Partly Sunny"),
    @SerializedName("PARTLY_SUNNY_RAIN")
    PARTLY_SUNNY_RAIN(0x1F326, "Party Sunny Rain"),
    @SerializedName("THUNDER_CLOUD_AND_RAIN")
    THUNDER_CLOUD_AND_RAIN(0x26C8, "Thunder Cloud And Rain"),
    @SerializedName("TORNADO")
    TORNADO(0x1F32A, "Tornado"),
    @SerializedName("BARELY_SUNNY")
    BARELY_SUNNY(0x1F325, "Barely Sunny"),
    @SerializedName("LIGHTENING")
    LIGHTENING(0x1F329, "Lightening"),
    @SerializedName("SNOW_CLOUD")
    SNOW_CLOUD(0x1F328, "Snow Cloud"),
    @SerializedName("RAINY")
    RAINY(0x1F327, "Rainy");
}