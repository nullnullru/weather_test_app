package com.utim.weathertestapp.data.local

import com.utim.weathertestapp.data.model.CityModel

interface CityLocalRepository {
    suspend fun getCities(query: String): List<CityModel>?
    fun saveResponse(query: String, cities: List<CityModel>)
}