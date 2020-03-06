package com.utim.weathertestapp.data.model

data class WeatherModel(
    var cityName: String,
    val temperature: Int,
    val weatherText: String,
    val params: List<WeatherParamModel>
)