package com.mobile.core.domain.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DeviceTokenRequest(
    @Expose @SerializedName("password")
    val password: String = "PiccardSkywarp742"
)

class ConfirmationCodeRequest(
    @Expose @SerializedName("device_token")
    val deviceToken: String?,
    @Expose @SerializedName("username")
    val username: String
) : Serializable

data class CheckCodeRequest(
    @Expose @SerializedName("device_token")
    val deviceToken: String?,
    @Expose @SerializedName("username")
    val username: String,
    @Expose @SerializedName("confirmation_code")
    val code: String
)

data class CheckUserRequest(
    @Expose @SerializedName("device_token")
    val deviceToken: String?,
    @Expose @SerializedName("username")
    val username: String
)

data class CreateUserRequest(
    @Expose @SerializedName("device_token")
    val deviceToken: String?,
    @Expose @SerializedName("username")
    val username: String,
    @Expose @SerializedName("password")
    val password: String,
    @Expose @SerializedName("language")
    val language: String
)

data class SetPasswordRequest(
    @Expose @SerializedName("password")
    val password: String
)

data class CreatePinRequest(
    @Expose @SerializedName("device_token")
    val deviceToken: String,
    @Expose @SerializedName("pin")
    val pin: String,
    @Expose @SerializedName("username")
    val username: String? = ""
)

data class AuthPinRequest(
    @Expose @SerializedName("device_token")
    val deviceToken: String,
    @Expose @SerializedName("pin")
    val pin: String,
    @Expose @SerializedName("username")
    val username: String
)