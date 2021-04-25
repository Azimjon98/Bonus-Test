package me.androiddev.data.di

import android.content.Context
import com.mobile.core.util.Connectivity
import dagger.Module
import dagger.Provides
import me.androiddev.core.common.Constants
import me.androiddev.core.di.CoreScope
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

@Module
class InterceptorModule {

    @Provides
    @CoreScope
    internal fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        connectivity: Connectivity,
        context: Context
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool())
            .addNetworkInterceptor(loggingInterceptor)
            .cache(cache)
            .addInterceptor { chain ->
                var originalRequest = chain.request()

                if (connectivity.hasNetworkAccess()) {
                    //Has connection
                    originalRequest = originalRequest.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5)
                        .header(
                            Constants.HEADER_ACCESS_KEY_NAME,
                            Constants.HEADER_ACCESS_KEY_VALUE
                        )
                        .addHeader("device-type", "android")
                        .build()

                } else {
                    //No connection

                    originalRequest =
                        originalRequest.newBuilder()
                            .header(
                                "Cache-Control",
                                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                            )
                            .header(
                                Constants.HEADER_ACCESS_KEY_NAME,
                                Constants.HEADER_ACCESS_KEY_VALUE
                            )
                            .addHeader("device-type", "android")
                            .build()


                }

                return@addInterceptor chain.proceed(originalRequest)
            }
            .build()

    @Provides
    @CoreScope
    internal fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @CoreScope
    fun provideCache(file: File): Cache {
        return Cache(file, 100 * 1024 * 1024) //1000mb
    }

    @Provides
    @CoreScope
    fun provideFile(context: Context): File {
        val file = File(context.cacheDir, "apiCache")
        file.mkdir()
        return file
    }
}