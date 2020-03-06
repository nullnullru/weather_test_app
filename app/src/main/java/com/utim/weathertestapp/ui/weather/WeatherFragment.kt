package com.utim.weathertestapp.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.utim.weathertestapp.R
import com.utim.weathertestapp.databinding.FragmentWeatherBinding
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment(R.layout.fragment_weather) {
    private lateinit var vm: WeatherViewModel
    private lateinit var adapter: WeatherParamAdapter
    private lateinit var binding: FragmentWeatherBinding

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
        binding = DataBindingUtil.bind(view)!!

        binding.isLoading = vm.getIsLoadingLiveData().value
        binding.isRefreshing = vm.getIsRefreshingLiveData().value

        vm.getIsLoadingLiveData().observe(this, Observer {
            binding.isLoading = it
        })
        vm.getIsRefreshingLiveData().observe(this, Observer {
            binding.isRefreshing = it
        })
        vm.getModelLiveData().observe(this, Observer {
            adapter.update(it.params)
            binding.model = it
        })
        recycler.adapter = adapter

        button_search.setOnClickListener {
            findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToSearchFragment(vm.cityName))
        }

        swipe_layout.setOnRefreshListener {
            swipe_layout.isRefreshing = false
            vm.refreshWeather()
        }
    }

    override fun onStart() {
        super.onStart()
        vm.loadWeather()
    }
}