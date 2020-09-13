package com.utim.weathertestapp.data.remote

import com.utim.weathertestapp.data.model.WeatherModel
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeather(locationKey: Int): Single<WeatherModel>
}