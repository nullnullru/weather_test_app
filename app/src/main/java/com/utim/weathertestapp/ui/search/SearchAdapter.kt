package com.utim.weathertestapp.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utim.weathertestapp.R
import com.utim.weathertestapp.data.model.CityModel
import com.utim.weathertestapp.databinding.ItemCityBinding
import com.utim.weathertestapp.util.diff.CityDiffCallback

class SearchAdapter(private val callback: OnCitySelectCallback? = null) :
    RecyclerView.Adapter<SearchAdapter.SearchItemViewHolder>() {
    private var cities: List<CityModel> = ArrayList()

    fun update(newData: List<CityModel>){
        val result = DiffUtil.calculateDiff(CityDiffCallback(cities, newData))
        cities = newData
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_city,
                parent,
                false
            ),
            callback
        )
    }

    override fun getItemCount() = cities.size

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    class SearchItemViewHolder(
        private val binding: ItemCityBinding,
        private val callback: OnCitySelectCallback?
    ) : RecyclerView.ViewHolder(binding.root) {
        private var cityModel: CityModel? = null

        init {
            binding.root.setOnClickListener {
                cityModel?.let {
                    callback?.onSelected(it)
                }
            }
        }

        fun bind(cityModel: CityModel) {
            this.cityModel = cityModel
            binding.cityName = cityModel.name
        }
    }

    interface OnCitySelectCallback {
        fun onSelected(cityModel: CityModel)
    }
}