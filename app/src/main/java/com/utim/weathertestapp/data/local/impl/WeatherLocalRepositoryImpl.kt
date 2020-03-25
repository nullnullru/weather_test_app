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

    override suspend fun getWeather(key: Int): WeatherModel? {
        if(Paper.book(BOOK_NAME).contains(key.toString())) {
            return Paper.book(BOOK_NAME).read<WeatherModel>(key.toString())
        }

        return null
    }

    override fun saveResponse(key: Int, weatherModel: WeatherModel) {
        Paper.book(BOOK_NAME).write(key.toString(), weatherModel)
    }
}