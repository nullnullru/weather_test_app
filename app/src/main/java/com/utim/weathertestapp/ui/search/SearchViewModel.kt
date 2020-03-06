package com.utim.weathertestapp.ui.search

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.remote.CityRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel : ViewModel() {
    @Inject
    lateinit var repository: CityRepository

    @Inject
    lateinit var localRepository: CityLocalRepository

    private var searchResult: MutableLiveData<List<CityModel>> = MutableLiveData()
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var lastSearchedCity: MutableLiveData<String> = MutableLiveData("")

    init {
        App.component.inject(this)
        searchResult.value = ArrayList()
    }

    fun getSearchResultLiveData(): LiveData<List<CityModel>> = searchResult
    fun getLoadingLiveData(): LiveData<Boolean> = isLoading
    fun getLastSearchedCityLiveData(): LiveData<String> = lastSearchedCity

    fun searchCity(cityName: String) {
        lastSearchedCity.value = cityName
        if(cityName.isNotEmpty()) {
            isLoading.value = true
            GlobalScope.launch {
                if(localRepository.hasResponse(cityName)) {
                    searchResult.postValue(localRepository.getCities(cityName))
                    isLoading.postValue(false)
                } else {
                    repository.getCities(cityName).let {
                        searchResult.postValue(it)
                        localRepository.saveResponse(cityName, it)
                        isLoading.postValue(false)
                    }
                }
            }
        } else {
            searchResult.value = ArrayList()
            isLoading.value = false
        }
    }
}