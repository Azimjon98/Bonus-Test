package me.androiddev.data.usecase

import me.androiddev.data.model.Result

interface BaseUseCase<in T : Any, out R : Any> {
    suspend operator fun invoke(param: T): Result<R>
}