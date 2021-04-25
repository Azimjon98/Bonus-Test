package me.androiddev.core.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import me.androiddev.core.di.CoreScope

@Module
class AppModule {

    @CoreScope
    @Provides
    fun bindContext(application: Application): Context = application
}


