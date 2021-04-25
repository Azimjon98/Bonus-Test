package me.androiddev.data.repository

import me.androiddev.data.model.Result
import me.androiddev.data.model.request.AccessTokenRequest
import me.androiddev.data.model.response.AccessTokenResponse
import me.androiddev.data.model.response.BonusResponse

interface Repository {
    suspend fun getAccessToken(param: AccessTokenRequest): Result<AccessTokenResponse>
    suspend fun getBonus(token : String): Result<BonusResponse>
}