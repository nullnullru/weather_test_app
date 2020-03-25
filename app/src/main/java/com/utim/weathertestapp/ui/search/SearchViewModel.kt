package com.utim.weathertestapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.model.ViewModelPriority
import com.utim.weathertestapp.data.remote.CityRepository
import com.utim.weathertestapp.ui.base.BaseDataViewModel
import javax.inject.Inject

class SearchViewModel : BaseDataViewModel<List<CityModel>>() {
    @Inject
    lateinit var repository: CityRepository
    @Inject
    lateinit var localRepository: CityLocalRepository

    private var lastSearchedCity: MutableLiveData<String> = MutableLiveData("")

    init {
        App.component.inject(this)
    }

    fun getLastSearchedCityLiveData(): LiveData<String> = lastSearchedCity

    fun searchCity(cityName: String) {
        lastSearchedCity.value = cityName
        if(cityName.isNotEmpty()) {
            loadData(ViewModelPriority.LOCAL)
        } else {
            data.value = ArrayList()
        }
    }

    override fun saveToLocal(data: List<CityModel>) = localRepository.saveResponse(lastSearchedCity.value!!, data)
    override suspend fun getData() = repository.getCities(lastSearchedCity.value!!)
    override suspend fun getLocalData() = localRepository.getCities(lastSearchedCity.value!!)
}