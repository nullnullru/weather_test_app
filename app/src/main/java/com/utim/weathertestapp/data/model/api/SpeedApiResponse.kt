package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class SpeedApiResponse(
    @SerializedName("Metric") val metric: MetricApiResponse
)