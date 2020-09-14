package com.utim.weathertestapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utim.weathertestapp.data.model.*
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseDataViewModel<T> : ViewModel() {

    private var state: MutableLiveData<UIState<T>> = MutableLiveData()

    init {
        state.value = InitState()
    }

    fun getStateLiveData(): LiveData<UIState<T>> = state

    protected fun loadData(priority: LoadingPriority = LoadingPriority.REMOTE): Disposable {

        val nextPriority = if (priority == LoadingPriority.REMOTE) LoadingPriority.LOCAL
        else LoadingPriority.REMOTE

        return modifyLoadObservable(
                getDataWithPriority(priority).onErrorResumeNext { getDataWithPriority(nextPriority) }
            )
            .doOnSubscribe {
                state.value = LoadingState()
            }
            .subscribe({
                when (it.priority) {
                    LoadingPriority.LOCAL -> postState(LocalState(it.data))
                    LoadingPriority.REMOTE -> postState(LoadedState(it.data))
                }
            }, {
                state.postValue(FailedState())
            })
    }

    private fun getDataWithPriority(priority: LoadingPriority): Single<Response<T>> {
        return if (isLocalPriority(priority))
            getLocalData()
                .map { Response(it, priority) }
        else getData()
            .map { Response(it, priority) }
            .doOnSuccess { saveToLocal(it.data) }
    }

    private fun isLocalPriority(priority: LoadingPriority): Boolean {
        return priority == LoadingPriority.LOCAL
    }

    protected open fun modifyLoadObservable(single: Single<Response<T>>): Single<Response<T>> {
        return single
    }

    protected open fun postState(state: UIState<T>) {
        this.state.postValue(state)
    }

    protected abstract fun saveToLocal(data: T)
    protected abstract fun getData(): Single<T>
    protected abstract fun getLocalData(): Single<T>

    protected class Response<T>(val data: T, val priority: LoadingPriority)
}