package com.utim.weathertestapp.data.remote

import com.utim.weathertestapp.BuildConfig
import com.utim.weathertestapp.data.model.api.CityApiResponse
import com.utim.weathertestapp.data.model.api.WeatherApiResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("locations/v1/cities/autocomplete")
    fun getCities(
        @Query("q") name: String,
        @Query("language") language: String = Locale.getDefault().language.plus("-").plus(Locale.getDefault().country).toLowerCase(Locale.getDefault()),
        @Query("apikey") apikey: String = BuildConfig.API_KEY
    ): Single<List<CityApiResponse>>

    @GET("currentconditions/v1/{key}")
    fun getWeather(
        @Path("key") locationKey: Int,
        @Query("details") details: Boolean = true,
        @Query("language") language: String = Locale.getDefault().language.plus("-").plus(Locale.getDefault().country).toLowerCase(Locale.getDefault()),
        @Query("apikey") apikey: String = BuildConfig.API_KEY
    ): Single<List<WeatherApiResponse>>

}
