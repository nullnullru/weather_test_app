package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class TempUnitApiModel(
    @SerializedName("Value") val value: Double,
    @SerializedName("Unit") val unit: String
)