package me.androiddev.data.usecase

import me.androiddev.data.model.response.BonusResponse
import me.androiddev.data.repository.Repository
import javax.inject.Inject

class GetBonusUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<String, BonusResponse> {

    override suspend fun invoke(param: String) = repository.getBonus(param)
}