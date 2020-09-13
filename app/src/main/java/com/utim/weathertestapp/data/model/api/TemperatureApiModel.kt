package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class TemperatureApiModel(
    @SerializedName("Metric") val metric: TempUnitApiModel
)