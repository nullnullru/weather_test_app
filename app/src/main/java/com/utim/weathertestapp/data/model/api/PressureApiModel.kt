package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class PressureApiModel(
    @SerializedName("Metric") val metric: TempUnitApiModel
)