package com.utim.weathertestapp.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.utim.weathertestapp.R

class BaseDataAdapter {
    companion object {
        @BindingAdapter(value = ["visibleIf"])
        @JvmStatic
        fun setVisibility(view: View, isVisible: Boolean) {
            view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        }

        @BindingAdapter(value = ["setTemperature"])
        @JvmStatic
        fun setTemperature(view: TextView, temperature: Int) {
            view.text = view.resources.getString(R.string.temperature).format(temperature)
        }
    }

}