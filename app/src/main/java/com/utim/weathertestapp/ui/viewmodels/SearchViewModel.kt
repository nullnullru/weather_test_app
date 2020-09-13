package com.utim.weathertestapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.model.LoadedState
import com.utim.weathertestapp.data.model.LoadingPriority
import com.utim.weathertestapp.data.remote.CityRepository
import io.reactivex.rxjava3.functions.Predicate
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
            loadData(LoadingPriority.LOCAL)
        } else {
            postState(LoadedState(listOf()))
        }
    }

    override fun filter(): Predicate<List<CityModel>> = Predicate { it.isNotEmpty() }
    override fun saveToLocal(data: List<CityModel>) = localRepository.saveResponse(lastSearchedCity.value!!, data)
    override fun getData() = repository.getCities(lastSearchedCity.value!!)
    override fun getLocalData() = localRepository.getCities(lastSearchedCity.value!!)
}