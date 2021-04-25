package me.androiddev.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccessTokenResponse(
    @Expose
    @SerializedName("accessToken")
    val accessToken: String,
    @Expose @SerializedName("result")
    val apiResult: ApiResult
) : Serializable