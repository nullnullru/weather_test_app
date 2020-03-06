package com.utim.weathertestapp.util.diff

import androidx.recyclerview.widget.DiffUtil
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.data.model.WeatherParamModel
import com.utim.weathertestapp.data.model.WeatherParamType

class ParamDiffCallback(
    private val oldList: List<WeatherParamModel>,
    private val newList: List<WeatherParamModel>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldPos: Int, newPos: Int) = oldList[oldPos].param == newList[newPos].param

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldPos: Int, newPos: Int) = oldList[oldPos].value == newList[newPos].value
}