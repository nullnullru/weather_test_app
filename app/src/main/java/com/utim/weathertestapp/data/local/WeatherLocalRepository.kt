package com.utim.weathertestapp.data.local

import com.utim.weathertestapp.data.model.WeatherModel
import io.reactivex.rxjava3.core.Single

interface WeatherLocalRepository {
    fun getWeather(key: Int): Single<WeatherModel>
    fun saveResponse(key: Int, weatherModel: WeatherModel)
}