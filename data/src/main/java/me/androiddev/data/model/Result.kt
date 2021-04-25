package me.androiddev.data.model

sealed class Result<out U : Any>
data class SuccessResult<out T : Any>(val data: T) : Result<T>()
data class Failure(val httpError: HttpError) : Result<Nothing>()

class HttpError(val throwable: Throwable, val errorCode: Int = 0)

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is SuccessResult) action(data)
    return this
}

inline fun <T : Any> Result<T>.onFailure(action: (HttpError) -> Unit) {
    if (this is Failure) action(httpError)
}