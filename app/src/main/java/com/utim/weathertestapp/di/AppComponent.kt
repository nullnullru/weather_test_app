package com.utim.weathertestapp.di

import com.utim.weathertestapp.ui.search.SearchFragment
import com.utim.weathertestapp.ui.search.SearchViewModel
import com.utim.weathertestapp.ui.weather.WeatherFragment
import com.utim.weathertestapp.ui.weather.WeatherParamAdapter
import com.utim.weathertestapp.ui.weather.WeatherViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RepoModule::class, AppModule::class])
@Singleton
interface AppComponent {
    // Fragments
    fun inject(fragment: SearchFragment)

    // View Models
    fun inject(viewModel: SearchViewModel)
    fun inject(viewModel: WeatherViewModel)

    // Adapters
    fun inject(adapter: WeatherParamAdapter)
}