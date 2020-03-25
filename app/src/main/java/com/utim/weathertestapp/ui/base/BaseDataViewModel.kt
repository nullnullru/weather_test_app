package com.utim.weathertestapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utim.weathertestapp.data.model.ViewModelPriority
import com.utim.weathertestapp.data.model.ViewModelState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseDataViewModel<T> : ViewModel() {
    protected var data: MutableLiveData<T> = MutableLiveData()
    private var state: MutableLiveData<ViewModelState> = MutableLiveData()

    init {
        state.value = ViewModelState.INIT
    }

    fun getDataLiveData(): LiveData<T> = data
    fun getStateLiveData(): LiveData<ViewModelState> = state

    protected fun loadData(priority: ViewModelPriority = ViewModelPriority.REMOTE) {
        state.value = ViewModelState.LOADING
        GlobalScope.launch {
            var hasAnyData = false

            getDataWithPriority(priority)?.let {
                postData(it)
                setStateBy(priority)
                hasAnyData = true
            }?:getDataWithPriority(priority, false)?.let {
                postData(it)
                setStateBy(priority, false)
                hasAnyData = true
            }

            if(!hasAnyData) {
                state.postValue(ViewModelState.FAILED)
            }
        }
    }

    protected open fun postData(data: T) {
        this.data.postValue(data)
    }

    private fun setStateBy(priority: ViewModelPriority, isFirstStep: Boolean = true) {
        state.postValue(
            if(isLocalPriority(priority, isFirstStep))
                ViewModelState.LOCAL
            else
                ViewModelState.LOADED
        )
    }

    private suspend fun getDataWithPriority(priority: ViewModelPriority, isFirstStep: Boolean = true): T? {
        val isLocal = isLocalPriority(priority, isFirstStep)

        val data: T? = if(isLocal) getLocalData() else getData()

        if(!isLocal && data != null) {
            saveToLocal(data)
        }

        return data
    }

    private fun isLocalPriority(
        priority: ViewModelPriority,
        isFirstStep: Boolean
    ): Boolean {
        return priority == ViewModelPriority.LOCAL && isFirstStep
                || priority == ViewModelPriority.REMOTE && !isFirstStep
    }

    protected abstract fun saveToLocal(data: T)
    protected abstract suspend fun getData(): T?
    protected abstract suspend fun getLocalData(): T?
}