package com.utim.weathertestapp.di

import com.utim.weathertestapp.ui.fragments.SearchFragment
import com.utim.weathertestapp.ui.viewmodels.SearchViewModel
import com.utim.weathertestapp.ui.adapters.WeatherParamAdapter
import com.utim.weathertestapp.ui.viewmodels.WeatherViewModel
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