package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class WindApiModel(
    @SerializedName("Speed") val speed: TempUnitApiModel
)