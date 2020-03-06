package com.utim.weathertestapp.data.local.impl

import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.model.WeatherModel
import io.paperdb.Paper

class WeatherLocalRepositoryImpl: WeatherLocalRepository {
    companion object {
        const val BOOK_NAME = "WEATHER"
    }

    override suspend fun getWeather(key: Int): WeatherModel {
        return Paper.book(BOOK_NAME).read<WeatherModel>(key.toString())
    }

    override fun hasResponse(key: Int): Boolean {
        return Paper.book(BOOK_NAME).allKeys.contains(key.toString())
    }

    override fun saveResponse(key: Int, weatherModel: WeatherModel) {
        Paper.book(BOOK_NAME).write(key.toString(), weatherModel)
    }
}