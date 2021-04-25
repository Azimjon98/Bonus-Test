package me.androiddev.data.remote

import me.androiddev.data.model.request.AccessTokenRequest
import me.androiddev.data.model.response.AccessTokenResponse
import me.androiddev.data.model.response.BonusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("clients/accesstoken")
    suspend fun getAccessToken(@Body body: AccessTokenRequest): Response<AccessTokenResponse>

    @GET("ibonus/generalinfo/{token}")
    suspend fun getBonus(@Path("token") token: String): Response<BonusResponse>
}