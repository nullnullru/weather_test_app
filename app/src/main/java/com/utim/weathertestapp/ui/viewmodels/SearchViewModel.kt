package com.utim.weathertestapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utim.weathertestapp.App
import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.model.LoadedState
import com.utim.weathertestapp.data.model.LoadingPriority
import com.utim.weathertestapp.data.remote.CityRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Predicate
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel : BaseDataViewModel<List<CityModel>>() {
    @Inject
    lateinit var repository: CityRepository
    @Inject
    lateinit var localRepository: CityLocalRepository

    private var lastSearchedCity: MutableLiveData<String> = MutableLiveData("")

    private var loadingDisposable: Disposable? = null

    init {
        App.component.inject(this)
    }

    fun getLastSearchedCityLiveData(): LiveData<String> = lastSearchedCity

    fun searchCity(cityName: String) {
        loadingDisposable?.dispose()

        lastSearchedCity.value = cityName
        if(cityName.isNotEmpty()) {
            loadingDisposable = loadData(LoadingPriority.LOCAL)
        } else {
            postState(LoadedState(listOf()))
        }
    }

    override fun modifyLoadObservable(single: Single<Response<List<CityModel>>>): Single<Response<List<CityModel>>> {
        return single.delay(500, TimeUnit.MILLISECONDS)
    }

    override fun saveToLocal(data: List<CityModel>) = localRepository.saveResponse(lastSearchedCity.value!!, data)
    override fun getData() = repository.getCities(lastSearchedCity.value!!)
    override fun getLocalData() = localRepository.getCities(lastSearchedCity.value!!)
}