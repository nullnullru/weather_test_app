package com.utim.weathertestapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utim.weathertestapp.App
import com.utim.weathertestapp.R
import com.utim.weathertestapp.data.model.WeatherParamModel
import com.utim.weathertestapp.data.model.WeatherParamType
import com.utim.weathertestapp.util.diff.ParamDiffCallback
import javax.inject.Inject

class WeatherParamAdapter : RecyclerView.Adapter<WeatherParamAdapter.WeatherParamViewHolder>() {
    @Inject
    lateinit var context: Context

    private var params: List<WeatherParamModel> = ArrayList()
    private val templates: Map<WeatherParamType, String>

    init {
        App.component.inject(this)
        templates = mapOf(
            WeatherParamType.WIND_SPEED to context.resources.getString(R.string.weather_param_wind_speed),
            WeatherParamType.REALFEEL to context.resources.getString(R.string.weather_param_realfeel),
            WeatherParamType.PRESSURE to context.resources.getString(R.string.weather_param_pressure),
            WeatherParamType.HUMIDITY to context.resources.getString(R.string.weather_param_humidity)
        )
    }

    fun update(newData: List<WeatherParamModel>){
        val result = DiffUtil.calculateDiff(ParamDiffCallback(params, newData))
        params = newData
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeatherParamViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_weather_param, parent, false),
            templates
        )

    override fun getItemCount() = params.size

    override fun onBindViewHolder(holder: WeatherParamViewHolder, position: Int) {
        holder.bind(params[position])
    }

    class WeatherParamViewHolder(view: View, val templates: Map<WeatherParamType, String>): RecyclerView.ViewHolder(view) {
        private val textParam: TextView = view as TextView

        fun bind(model: WeatherParamModel) {
            templates[model.param]?.let {
                textParam.text = String.format(it, model.value)
            }
        }
    }
}