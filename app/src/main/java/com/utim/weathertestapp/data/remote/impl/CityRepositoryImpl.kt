package com.utim.weathertestapp.data.remote.impl

import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.remote.ApiService
import com.utim.weathertestapp.data.remote.CityRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CityRepositoryImpl(private val apiService: ApiService) : CityRepository {

    override fun getCities(cityName: String): Single<List<CityModel>> {
        return apiService.getCities(cityName)
            .map { list -> list.map { CityModel(it.localizedName, it.key) } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}