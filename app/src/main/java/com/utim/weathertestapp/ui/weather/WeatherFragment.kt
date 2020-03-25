package com.utim.weathertestapp.ui.weather

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utim.weathertestapp.R
import com.utim.weathertestapp.data.model.ViewModelState
import com.utim.weathertestapp.databinding.FragmentWeatherBinding
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var vm: WeatherViewModel
    private lateinit var adapter: WeatherParamAdapter
    private var binding: FragmentWeatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        arguments?.let {
            vm.init(
                it.getInt("key"),
                it.getString("name", "")
            )
        }
        adapter = WeatherParamAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = adapter
        binding = DataBindingUtil.bind(view)

        vm.getDataLiveData().observe(this, Observer { model ->
            binding?.model = model
            adapter.update(model.params)
        })

        vm.getStateLiveData().observe(this, Observer { state ->
            binding?.state = state
            if(state == ViewModelState.INIT) {
                vm.loadWeather()
            } else if(state == ViewModelState.FAILED) {
                Snackbar.make(
                    view,
                    getString(R.string.error_getting_data),
                    Snackbar.LENGTH_LONG
                ).show()
            }

            if(state == ViewModelState.LOADED || state == ViewModelState.LOCAL) {
                swipe_layout.isRefreshing = false
            }
        })

        button_search.setOnClickListener {
            findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToSearchFragment(vm.cityName))
        }

        swipe_layout.setOnRefreshListener {
            vm.loadWeather()
        }
    }
}