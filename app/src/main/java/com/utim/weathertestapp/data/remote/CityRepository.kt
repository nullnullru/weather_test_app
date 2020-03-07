package com.utim.weathertestapp.data.remote

import com.utim.weathertestapp.data.model.CityModel

interface CityRepository {
    suspend fun getCities(cityName: String): List<CityModel>?
}