package com.utim.weathertestapp.data.remote.impl

import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.remote.ApiService
import com.utim.weathertestapp.data.remote.CityRepository

class CityRepositoryImpl(private val apiService: ApiService) : CityRepository {
    override suspend fun getCities(cityName: String): List<CityModel>? {
        try {
            val response = apiService.getCities(cityName).execute()
            response.body()?.let {
                return@getCities it.map { cityApiResp ->
                    CityModel(cityApiResp.localizedName, cityApiResp.key)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}