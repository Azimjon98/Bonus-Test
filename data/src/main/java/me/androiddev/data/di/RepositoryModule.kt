package me.androiddev.data.di

import dagger.Module
import dagger.Provides
import me.androiddev.core.di.CoreScope
import me.androiddev.data.remote.ApiService
import me.androiddev.data.repository.Repository
import me.androiddev.data.repository.RepositoryImpl

import retrofit2.Retrofit

@Module
class RepositoryModule {

    @Provides
    @CoreScope
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @CoreScope
    fun bindRepository(
        apiService: ApiService
    ): Repository {
        return RepositoryImpl(
            apiService = apiService
        )
    }
}