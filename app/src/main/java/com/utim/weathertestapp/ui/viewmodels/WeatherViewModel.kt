package com.utim.weathertestapp.ui.viewmodels

import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.model.LoadedState
import com.utim.weathertestapp.data.model.LocalState
import com.utim.weathertestapp.data.model.UIState
import com.utim.weathertestapp.data.model.WeatherModel
import com.utim.weathertestapp.data.remote.WeatherRepository
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

    fun setShownLocalWarning() {
        val currentState = getStateLiveData().value
        if (currentState is LocalState) {
            currentState.shownLocalWarning = true
            postState(currentState)
        }
    }

    override fun postState(state: UIState<WeatherModel>) {
        when(state) {
            is LoadedState -> {
                state.data.cityName = cityName
            }
            is LocalState -> {
                state.data.cityName = cityName
            }
        }

        super.postState(state)
    }

    override fun saveToLocal(data: WeatherModel) = weatherLocalRepository.saveResponse(cityKey, data)
    override fun getData() = weatherRepository.getWeather(cityKey)
    override fun getLocalData() = weatherLocalRepository.getWeather(cityKey)
}