package com.utim.weathertestapp.data.remote.impl

import com.utim.weathertestapp.data.model.WeatherModel
import com.utim.weathertestapp.data.model.WeatherParamModel
import com.utim.weathertestapp.data.model.WeatherParamType
import com.utim.weathertestapp.data.remote.ApiService
import com.utim.weathertestapp.data.remote.WeatherRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class WeatherRepositoryImpl(private val apiService: ApiService) : WeatherRepository {

    override fun getWeather(locationKey: Int): Single<WeatherModel> {
        return apiService.getWeather(locationKey)
            .map {
                WeatherModel(
                    "",
                    it[0].temperature.metric.value.toInt(),
                    it[0].weatherText,
                    listOf(
                        WeatherParamModel(
                            WeatherParamType.REALFEEL,
                            it[0].realFeel.metric.value.toInt()
                        ),
                        WeatherParamModel(WeatherParamType.HUMIDITY, it[0].humidity),
                        WeatherParamModel(
                            WeatherParamType.WIND_SPEED,
                            it[0].wind.speed.value.toInt()
                        ),
                        WeatherParamModel(
                            WeatherParamType.PRESSURE,
                            it[0].pressure.metric.value.toInt()
                        )
                    )
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}