package com.utim.weathertestapp.data.remote.impl

import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.remote.ApiService
import com.utim.weathertestapp.data.remote.CityRepository

class CityRepositoryImpl(private val apiService: ApiService) : CityRepository {
    override suspend fun getCities(cityName: String): List<CityModel> {
        var cities: List<CityModel> = ArrayList()

        apiService.getCities(cityName).execute().body()?.let {
            cities = it.map { cityApiResp ->
                CityModel(cityApiResp.localizedName, cityApiResp.key)
            }
        }

        return cities
    }
}