package com.utim.weathertestapp.data.remote.impl

import com.utim.weathertestapp.data.model.WeatherModel
import com.utim.weathertestapp.data.model.WeatherParamModel
import com.utim.weathertestapp.data.model.WeatherParamType
import com.utim.weathertestapp.data.remote.ApiService
import com.utim.weathertestapp.data.remote.WeatherRepository

class WeatherRepositoryImpl(private val apiService: ApiService) : WeatherRepository {
    override suspend fun getWeather(locationKey: Int): WeatherModel? {
        val apiModel = apiService.getWeather(locationKey).execute().body()

        if(apiModel != null && apiModel.isNotEmpty()) {
            return WeatherModel(
                "",
                apiModel[0].temperature.metric.value.toInt(),
                apiModel[0].weatherText,
                listOf(
                    WeatherParamModel(WeatherParamType.REALFEEL, apiModel[0].realFeel.metric.value.toInt()),
                    WeatherParamModel(WeatherParamType.HUMIDITY, apiModel[0].humidity),
                    WeatherParamModel(WeatherParamType.WIND_SPEED, apiModel[0].wind.speed.value.toInt()),
                    WeatherParamModel(WeatherParamType.PRESSURE, apiModel[0].pressure.metric.value.toInt())
                )
            )
        }

        return null
    }
}