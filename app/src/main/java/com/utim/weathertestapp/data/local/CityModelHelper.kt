package com.utim.weathertestapp.data.local

import android.content.Context
import android.content.SharedPreferences
import com.utim.weathertestapp.data.model.CityModel

class CityModelHelper(context: Context) {
    private val sp: SharedPreferences = context.getSharedPreferences("SPH", 0)

    fun saveCityModel(cityModel: CityModel) {
        sp.edit()
            .putString("name", cityModel.name)
            .putInt("key", cityModel.key)
            .apply()
    }

    fun getCityModel(): CityModel? {
        val cityModel = CityModel(sp.getString("name", "")!!, sp.getInt("key", 0))
        return if(cityModel.key == 0) null else cityModel
    }

    fun clear() {
        sp.edit().clear().apply()
    }
}