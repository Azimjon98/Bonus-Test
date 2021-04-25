package me.androiddev.core.mvvm

sealed class UIState<out T : Any>
class Success<out T : Any>(val data: T) : UIState<T>()
class Error<out T : Any>(val error: Throwable) : UIState<T>()
class Loading<out T : Any> : UIState<T>()
class NoInternetState<T : Any> : UIState<T>()