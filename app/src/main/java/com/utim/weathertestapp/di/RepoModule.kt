package com.utim.weathertestapp.di

import android.content.Context
import com.utim.weathertestapp.data.local.CityLocalRepository
import com.utim.weathertestapp.data.local.CityModelHelper
import com.utim.weathertestapp.data.local.WeatherLocalRepository
import com.utim.weathertestapp.data.local.impl.CityLocalRepositoryImpl
import com.utim.weathertestapp.data.local.impl.WeatherLocalRepositoryImpl
import com.utim.weathertestapp.data.remote.ApiService
import com.utim.weathertestapp.data.remote.CityRepository
import com.utim.weathertestapp.data.remote.WeatherRepository
import com.utim.weathertestapp.data.remote.impl.CityRepositoryImpl
import com.utim.weathertestapp.data.remote.impl.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Provides
    @Singleton
    fun provideCityRepo(apiService: ApiService): CityRepository {
        return CityRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCityLocalRepo(): CityLocalRepository {
        return CityLocalRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideWeatherRepo(apiService: ApiService): WeatherRepository {
        return WeatherRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherLocalRepo(): WeatherLocalRepository {
        return WeatherLocalRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCityModelHelper(context: Context): CityModelHelper {
        return CityModelHelper(context)
    }
}