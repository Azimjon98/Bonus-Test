package me.androiddev.data.repository

import me.androiddev.data.model.Result
import me.androiddev.data.model.request.AccessTokenRequest
import me.androiddev.data.model.response.AccessTokenResponse
import me.androiddev.data.model.response.BonusResponse
import me.androiddev.data.remote.ApiService
import me.androiddev.data.remote.getOptionalData


class RepositoryImpl constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getAccessToken(param: AccessTokenRequest): Result<AccessTokenResponse> {
        return apiService.getAccessToken(param).getOptionalData()
    }

    override suspend fun getBonus(token: String): Result<BonusResponse> {
        return apiService.getBonus(token).getOptionalData()
    }

}