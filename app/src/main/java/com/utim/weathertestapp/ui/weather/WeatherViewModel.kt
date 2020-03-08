package com.utim.weathertestapp.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.model.WeatherModel
import com.utim.weathertestapp.data.remote.WeatherRepository
import kotlinx.coroutines.Dispatchers
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
    private val errorCounter: MutableLiveData<Int> = MutableLiveData(0)

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
    fun getErrorCounterLiveData(): LiveData<Int> = errorCounter

    fun init(cityKey: Int, cityName: String){
        this.cityKey = cityKey
        this.cityName = cityName
    }

    fun resetCounter() {
        errorCounter.value = 0
    }

    fun loadWeather(){
        GlobalScope.launch {
            if(weatherLocalRepository.hasResponse(cityKey)) {
                isLoading.postValue(false)
                model.postValue(weatherLocalRepository.getWeather(cityKey))
                refreshWeather()
            } else {
                getWeather()
            }
        }
    }

    fun refreshWeather(){
        isRefreshing.postValue(true)
        GlobalScope.launch {
            getWeather()
        }
    }

    private suspend fun getWeather(){
        val model: WeatherModel? = weatherRepository.getWeather(cityKey)

        if(model != null) {
            model.cityName = cityName
            this@WeatherViewModel.model.postValue(model)

            weatherLocalRepository.saveResponse(cityKey, model)
        } else {
            errorCounter.postValue(errorCounter.value!!.plus(1))
        }

        isRefreshing.postValue(false)
        isLoading.postValue(false)
    }
}