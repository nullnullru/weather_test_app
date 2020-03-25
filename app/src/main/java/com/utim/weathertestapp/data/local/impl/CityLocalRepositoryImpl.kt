package com.utim.weathertestapp.data.local.impl

import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import io.paperdb.Paper

class CityLocalRepositoryImpl: CityLocalRepository {
    companion object {
        const val BOOK_NAME = "CITIES"
    }

    override suspend fun getCities(query: String): List<CityModel>? {
        if(Paper.book(BOOK_NAME).contains(query)) {
            return Paper.book(BOOK_NAME).read<List<CityModel>>(query, ArrayList())
        }

        return null
    }

    override fun saveResponse(query: String, cities: List<CityModel>) {
        Paper.book(BOOK_NAME).write(query, cities)
    }
}