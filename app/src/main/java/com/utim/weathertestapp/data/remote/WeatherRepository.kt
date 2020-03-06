package com.utim.weathertestapp.data.remote

import com.utim.weathertestapp.data.model.WeatherModel

interface WeatherRepository {
    suspend fun getWeather(locationKey: Int): WeatherModel?
}