package com.utim.weathertestapp.ui.adapters

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.utim.weathertestapp.R
import com.utim.weathertestapp.data.model.LoadedState
import com.utim.weathertestapp.data.model.LoadingState
import com.utim.weathertestapp.data.model.LocalState
import com.utim.weathertestapp.data.model.UIState

class BindingsAdapter {
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

        @BindingAdapter(value = ["invisibleIfLoading"])
        @JvmStatic
        fun setStateInvisibleIfLoading(view: View, state: UIState<*>) {
            when(state) {
                is LoadingState -> view.visibility = View.INVISIBLE
                else -> view.visibility = View.VISIBLE
            }
        }

        @BindingAdapter(value = ["visibleIfLoading"])
        @JvmStatic
        fun setStateVisibleIfLoading(view: View, state: UIState<*>) {
            when(state) {
                is LoadingState -> view.visibility = View.VISIBLE
                else -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter(value = ["visibleIfHasData"])
        @JvmStatic
        fun setStateVisibleIfHasData(view: View, state: UIState<*>) {
            when(state) {
                is LoadedState -> view.visibility = View.VISIBLE
                is LocalState -> view.visibility = View.VISIBLE
                else -> view.visibility = View.INVISIBLE
            }
        }
    }
}