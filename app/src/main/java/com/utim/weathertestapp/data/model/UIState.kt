package com.utim.weathertestapp.data.model

sealed class UIState<T>

class InitState<T> : UIState<T>()
class LoadingState<T> : UIState<T>()
class LoadedState<T>(val data: T) : UIState<T>()
class LocalState<T>(val data: T, var shownLocalWarning: Boolean = false) : UIState<T>()
class FailedState<T> : UIState<T>()
