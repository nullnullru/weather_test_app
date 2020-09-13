package com.utim.weathertestapp.data.remote

import com.utim.weathertestapp.data.model.CityModel
import io.reactivex.rxjava3.core.Single

interface CityRepository {

    fun getCities(cityName: String): Single<List<CityModel>>
}