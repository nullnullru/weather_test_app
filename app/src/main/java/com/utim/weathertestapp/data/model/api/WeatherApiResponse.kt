package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    @SerializedName("RealFeelTemperature") val realFeel: RealFeelTemperatureApiResponse,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("Temperature") val temperature: TemperatureApiResponse,
    @SerializedName("Wind") val wind: WindApiResponse,
    @SerializedName("UVIndex") val uvIndex: Int,
    @SerializedName("RelativeHumidity") val humidity: Int,
    @SerializedName("Pressure") val pressure: PressureApiResponse
)