package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    @SerializedName("RealFeelTemperature") val realFeel: RealFeelTemperatureApiModel,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("Temperature") val temperature: TemperatureApiModel,
    @SerializedName("Wind") val wind: WindApiModel,
    @SerializedName("UVIndex") val uvIndex: Int,
    @SerializedName("RelativeHumidity") val humidity: Int,
    @SerializedName("Pressure") val pressure: PressureApiModel
)