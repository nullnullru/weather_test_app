package com.utim.weathertestapp.util.diff

import androidx.recyclerview.widget.DiffUtil
import com.utim.weathertestapp.data.model.CityModel

class CityDiffCallback(
    private val oldList: List<CityModel>,
    private val newList: List<CityModel>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldPos: Int, newPos: Int) = oldList[oldPos].key == newList[newPos].key

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    // Не может быть у одного keyid города два разных названия, проверять это не нужно.
    override fun areContentsTheSame(oldPos: Int, newPos: Int) = true
}