package com.utim.weathertestapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utim.weathertestapp.data.model.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Predicate

abstract class BaseDataViewModel<T> : ViewModel() {

    private var state: MutableLiveData<UIState<T>> = MutableLiveData()

    init {
        state.value = InitState()
    }

    fun getStateLiveData(): LiveData<UIState<T>> = state

    protected fun loadData(priority: LoadingPriority = LoadingPriority.REMOTE) {
        state.value = LoadingState()

        val nextPriority = if(priority == LoadingPriority.REMOTE) LoadingPriority.LOCAL
        else LoadingPriority.REMOTE

        getDataWithPriority(priority)
            .onErrorResumeNext {
                getDataWithPriority(nextPriority)
            }
            .subscribe({
                when(it.priority) {
                    LoadingPriority.LOCAL -> postState(LocalState(it.data))
                    LoadingPriority.REMOTE -> postState(LoadedState(it.data))
                }
            }, {
                state.postValue(FailedState())
            })
    }

    protected open fun postState(state: UIState<T>) {
        this.state.postValue(state)
    }

    private fun getDataWithPriority(priority: LoadingPriority): Single<Response<T>> {
        return if(isLocalPriority(priority))
            getLocalData()
                .map { Response(it, priority) }
        else getData()
                .map { Response(it, priority) }
                .doOnSuccess { saveToLocal(it.data) }
    }

    private fun isLocalPriority(priority: LoadingPriority): Boolean {
        return priority == LoadingPriority.LOCAL
    }

    protected open fun filter(): Predicate<T> =  Predicate { it != null }
    protected abstract fun saveToLocal(data: T)
    protected abstract fun getData(): Single<T>
    protected abstract fun getLocalData(): Single<T>

    private class Response<T>(val data: T, val priority: LoadingPriority)
}