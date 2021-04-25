package me.androiddev.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AccessTokenRequest(
    @Expose @SerializedName("idClient")
    val clientId: String,
    @Expose @SerializedName("accessToken")
    val accessToken: String,
    @Expose @SerializedName("paramName")
    val name: String,
    @Expose @SerializedName("paramValue")
    val value: String,
    @Expose @SerializedName("latitude")
    val latitude: Int,
    @Expose @SerializedName("longitude")
    val longitude: Int,
    @Expose @SerializedName("sourceQuery")
    val sourceQuery: Int
) : Serializable