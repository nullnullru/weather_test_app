package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class RealFeelTemperatureApiResponse(
    @SerializedName("Metric") val metric: MetricApiResponse
)