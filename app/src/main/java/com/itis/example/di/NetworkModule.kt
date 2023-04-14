package com.itis.example.di

import com.itis.example.BuildConfig
import com.itis.example.data.core.interceptor.ApiKeyInterceptor
import com.itis.example.data.core.interceptor.UnitsInterceptor
import com.itis.example.data.weather.datasource.remote.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @Named("logger")
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Named("api_key")
    fun provideApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()

    @Provides
    @Named("units")
    fun provideUnitsInterceptor(): Interceptor = UnitsInterceptor()

    @Provides
    fun provideHttpClient(
        @Named("api_key") apiKeyInterceptor: Interceptor,
        @Named("logger") loggingInterceptor: Interceptor,
        @Named("units") unitsInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(unitsInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideBaseUrl(): String = BuildConfig.API_ENDPOINT

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gsonFactory: GsonConverterFactory,
        baseUrl: String
    ): Retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(gsonFactory)
        .build()

    @Provides
    fun provideWeatherApi(
        retrofit: Retrofit
    ): WeatherApi = retrofit.create(WeatherApi::class.java)
}
