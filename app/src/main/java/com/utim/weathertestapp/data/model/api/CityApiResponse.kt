package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class CityApiResponse(
    @SerializedName("Key") var key: Int = 0,
    @SerializedName("LocalizedName") var localizedName: String = ""
)