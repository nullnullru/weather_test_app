package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class PressureApiResponse(
    @SerializedName("Metric") val metric: MetricApiResponse
)