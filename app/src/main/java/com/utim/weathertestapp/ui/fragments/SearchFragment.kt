package com.utim.weathertestapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utim.weathertestapp.App
import com.utim.weathertestapp.R
import com.utim.weathertestapp.data.local.CityModelHelper
import com.utim.weathertestapp.data.model.*
import com.utim.weathertestapp.databinding.FragmentSearchCityBinding
import com.utim.weathertestapp.ui.adapters.SearchAdapter
import com.utim.weathertestapp.ui.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_city.*
import javax.inject.Inject


class SearchFragment : Fragment(R.layout.fragment_search_city), TextWatcher {
    private lateinit var vm: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private var binding: FragmentSearchCityBinding? = null

    @Inject
    lateinit var cityModelHelper: CityModelHelper

    init {
        App.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        adapter = SearchAdapter(object :
            SearchAdapter.OnCitySelectCallback {
            override fun onSelected(cityModel: CityModel) {
                cityModelHelper.saveCityModel(cityModel)
                edit_query?.clearFocus()

                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToWeatherFragment(
                        cityModel.key,
                        cityModel.name
                    )
                )
            }
        })
        arguments?.let {
            it.getString("name")?.let { name ->
                vm.searchCity(name)
            }
        }
        if (arguments?.getString("name") == null) {
            cityModelHelper.getCityModel()?.let {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToWeatherFragment(
                        it.key,
                        it.name
                    )
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        recycler.adapter = adapter

        vm.getStateLiveData().observe(viewLifecycleOwner, Observer { state ->
            binding?.state = state

            when(state) {
                is FailedState -> {
                    Snackbar.make(
                        view,
                        getString(R.string.error_getting_data),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                is LoadedState -> {
                    adapter.update(state.data)
                    recycler.layoutManager?.scrollToPosition(0)
                }
                is LocalState -> {
                    adapter.update(state.data)
                    recycler.layoutManager?.scrollToPosition(0)
                }
            }
        })

        vm.getLastSearchedCityLiveData().observe(viewLifecycleOwner, Observer {
            binding?.isEmpty = it.isEmpty()
        })

        edit_query.setText(vm.getLastSearchedCityLiveData().value)
        edit_query.addTextChangedListener(this)
        button_clear.setOnClickListener {
            edit_query.setText("")
        }
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        vm.searchCity(s.toString())
        binding?.isEmpty = s.toString().isEmpty()
    }
}