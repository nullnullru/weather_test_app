package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class WindApiResponse(
    @SerializedName("Speed") val speed: MetricApiResponse
)