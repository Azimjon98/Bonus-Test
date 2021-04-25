package me.androiddev.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResult(
    @Expose val status: Int,
    @Expose val message: String?,
    @Expose val messageDev: String?,
    @Expose val codeResult: Int,
    @Expose val duration: Double?,
    @Expose val idLog: String?
) : Serializable