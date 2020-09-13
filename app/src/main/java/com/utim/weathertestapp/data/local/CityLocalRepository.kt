package com.utim.weathertestapp.data.local

import com.utim.weathertestapp.data.model.CityModel
import io.reactivex.rxjava3.core.Single

interface CityLocalRepository {
    fun getCities(query: String): Single<List<CityModel>>
    fun saveResponse(query: String, cities: List<CityModel>)
}