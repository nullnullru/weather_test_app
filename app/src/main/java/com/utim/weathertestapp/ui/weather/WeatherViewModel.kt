package com.utim.weathertestapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.model.WeatherModel
import com.utim.weathertestapp.data.remote.WeatherRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel: ViewModel() {
    var cityKey: Int = 0
        private set
    var cityName: String = ""
        private set

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    private val isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    private val model: MutableLiveData<WeatherModel> = MutableLiveData()

    @Inject
    lateinit var weatherRepository: WeatherRepository
    @Inject
    lateinit var weatherLocalRepository: WeatherLocalRepository

    init {
        App.component.inject(this)
    }

    fun getIsLoadingLiveData(): LiveData<Boolean> = isLoading
    fun getIsRefreshingLiveData(): LiveData<Boolean> = isRefreshing
    fun getModelLiveData(): LiveData<WeatherModel> = model

    fun init(cityKey: Int, cityName: String){
        this.cityKey = cityKey
        this.cityName = cityName
    }

    fun loadWeather(){
        GlobalScope.launch {
            if(weatherLocalRepository.hasResponse(cityKey)) {
                isLoading.postValue(false)
                model.postValue(weatherLocalRepository.getWeather(cityKey))
                refreshWeather()
            } else {
                weatherRepository.getWeather(cityKey)?.let {
                    isLoading.postValue(false)
                    it.cityName = cityName
                    model.postValue(it)

                    weatherLocalRepository.saveResponse(cityKey, it)
                }
            }
        }
    }

    fun refreshWeather(){
        isRefreshing.postValue(true)
        GlobalScope.launch {
            weatherRepository.getWeather(cityKey)?.let {
                it.cityName = cityName
                model.postValue(it)
                isRefreshing.postValue(false)
            }
        }
    }
}