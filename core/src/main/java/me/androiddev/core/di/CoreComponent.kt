package me.androiddev.core.di

import android.app.Application
import android.content.Context
import com.mobile.core.util.Connectivity
import dagger.BindsInstance
import dagger.Component
import me.androiddev.core.App
import me.androiddev.core.di.CoreScope
import me.androiddev.core.di.module.AppModule
import me.androiddev.logintestapp.di.module.ConnectivityBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit


@CoreScope
@Component(modules = [AppModule::class, ConnectivityBuilder::class])
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): CoreComponent
    }

    fun inject(app: App)
    fun getConnectivity(): Connectivity
    fun getContext(): Context
}