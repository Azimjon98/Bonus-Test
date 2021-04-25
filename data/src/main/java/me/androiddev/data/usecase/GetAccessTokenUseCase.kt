package me.androiddev.data.usecase

import me.androiddev.data.model.request.AccessTokenRequest
import me.androiddev.data.model.response.AccessTokenResponse
import me.androiddev.data.repository.Repository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<AccessTokenRequest, AccessTokenResponse> {

    override suspend fun invoke(param: AccessTokenRequest) = repository.getAccessToken(param)
}