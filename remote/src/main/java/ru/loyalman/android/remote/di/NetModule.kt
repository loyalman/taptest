package ru.loyalman.android.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.loyalman.android.base.BuildConfig
import ru.loyalman.android.remote.NetworkApi
import timber.log.Timber
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Timber.d("Net: $message")
                    }
                }).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(logInterceptor)
            }
            build()
        }
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): NetworkApi {
        return Retrofit.Builder()
            .baseUrl("https://www.youtube.com/")
            .addConverterFactory(FACTORY)
            .client(okHttpClient)
            .build()
            .create(NetworkApi::class.java)
    }
}

data class Page(var content: String)

class PageAdapter : Converter<ResponseBody?, Page?> {


    override fun convert(value: ResponseBody?): Page {
        return Page(value?.string() ?: "")
    }

}

val FACTORY: Converter.Factory = object : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): PageAdapter {
        return PageAdapter()
    }
}