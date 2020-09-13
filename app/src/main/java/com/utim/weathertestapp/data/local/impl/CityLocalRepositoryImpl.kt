package com.utim.weathertestapp.data.local.impl

import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single
import java.lang.NullPointerException

class CityLocalRepositoryImpl: CityLocalRepository {

    companion object {
        const val BOOK_NAME = "CITIES"
    }

    override fun getCities(query: String): Single<List<CityModel>> {
        Paper.book(BOOK_NAME).read<List<CityModel>>(query) ?.let {
            return@getCities Single.just(it)
        } .run {
            return Single.error(NullPointerException("Empty local storage"))
        }
    }

    override fun saveResponse(query: String, cities: List<CityModel>) {
        Paper.book(BOOK_NAME).write(query, cities)
    }
}