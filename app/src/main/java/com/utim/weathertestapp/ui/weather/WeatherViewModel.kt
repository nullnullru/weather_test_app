package com.utim.weathertestapp.ui.weather

import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.model.WeatherModel
import com.utim.weathertestapp.data.remote.WeatherRepository
import com.utim.weathertestapp.ui.base.BaseDataViewModel
import javax.inject.Inject

class WeatherViewModel : BaseDataViewModel<WeatherModel>() {
    @Inject
    lateinit var weatherRepository: WeatherRepository
    @Inject
    lateinit var weatherLocalRepository: WeatherLocalRepository

    var cityKey: Int = 0
        private set
    var cityName: String = ""
        private set

    init {
        App.component.inject(this)
    }

    fun init(cityKey: Int, cityName: String){
        this.cityKey = cityKey
        this.cityName = cityName
    }

    fun loadWeather() {
        loadData()
    }

    override fun postData(data: WeatherModel) {
        data.cityName = cityName
        super.postData(data)
    }

    override fun saveToLocal(data: WeatherModel) = weatherLocalRepository.saveResponse(cityKey, data)
    override suspend fun getData() = weatherRepository.getWeather(cityKey)
    override suspend fun getLocalData() = weatherLocalRepository.getWeather(cityKey)

}