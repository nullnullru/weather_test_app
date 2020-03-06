package com.utim.weathertestapp.data.model.api

import com.google.gson.annotations.SerializedName

data class CityApiRequest(
    @SerializedName("query")
    val query: String,
    @SerializedName("from_bound")
    val fromBound: String = "city",
    @SerializedName("to_bound")
    val toBound: String = "city"
)
