package com.utim.weathertestapp.data.local

import com.utim.weathertestapp.data.model.WeatherModel

interface WeatherLocalRepository {
    suspend fun getWeather(key: Int): WeatherModel
    fun hasResponse(key: Int): Boolean
    fun saveResponse(key: Int, weatherModel: WeatherModel)
}