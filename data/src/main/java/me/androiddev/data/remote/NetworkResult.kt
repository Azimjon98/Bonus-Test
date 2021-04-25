package me.androiddev.data.remote

import me.androiddev.data.model.Failure
import me.androiddev.data.model.HttpError
import me.androiddev.data.model.Result
import me.androiddev.data.model.SuccessResult
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
    if (isSuccessful.not())
        errorBody()?.run {
            val body = this.string()
            val message: String =
                if (
                    body.isBlank() ||
                    (JSONObject(body).has("message").not())
                ) ""
                else {
                    JSONObject(body).getString("message")
                }
            action(HttpError(Throwable(message), code()))
        }
}

fun <T : DomainMapper<R>, R : Any> Response<T>.getData(): Result<R> {
    try {
        onSuccess { return SuccessResult(it.mapToDomainModel()) }
        onFailure { return Failure(it) }
        return Failure(HttpError(Throwable("Internet connection error")))
    } catch (e: Exception) {
        return Failure(HttpError(Throwable("Internet connection error")))
    }
}

// todo only for void requests
fun <T : Any> Response<T>.getOptionalData(): Result<T> {
    try {
        onSuccess { return SuccessResult(it) }
        onFailure { return Failure(it) }
        return Failure(HttpError(Throwable("Internet connection error")))
    } catch (e: IOException) {
        return Failure(HttpError(Throwable("Internet connection error")))
    }
}
