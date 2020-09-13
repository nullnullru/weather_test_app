package com.utim.weathertestapp.data.local.impl

import android.util.Log
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.model.WeatherModel
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single
import java.lang.NullPointerException

class WeatherLocalRepositoryImpl: WeatherLocalRepository {

    companion object {
        const val BOOK_NAME = "WEATHER"
    }

    override fun getWeather(key: Int): Single<WeatherModel> {
        Paper.book(BOOK_NAME).read<WeatherModel>(key.toString()) ?.let {
            return@getWeather Single.just(it)
        } .run {
            return@getWeather Single.error(NullPointerException("Empty local storage"))
        }
    }

    override fun saveResponse(key: Int, weatherModel: WeatherModel) {
        Paper.book(BOOK_NAME).write(key.toString(), weatherModel)
    }
}