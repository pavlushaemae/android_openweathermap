package com.itis.example.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.itis.example.BuildConfig
import com.itis.example.data.weather.datasource.remote.WeatherApi
import com.itis.example.data.core.interceptor.ApiKeyInterceptor
import com.itis.example.data.core.interceptor.UnitsInterceptor
import com.itis.example.data.location.LocationRepositoryImpl
import com.itis.example.data.location.datasource.LocationDataSource
import com.itis.example.data.weather.WeatherRepositoryImpl
import com.itis.example.domain.location.GetLocationUseCase
import com.itis.example.domain.weather.GetWeatherByIdUseCase
import com.itis.example.domain.weather.GetWeatherByNameUseCase
import com.itis.example.domain.weather.GetWeatherListUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DataContainer {

    private const val BASE_URL = BuildConfig.API_ENDPOINT

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(UnitsInterceptor())
            .connectTimeout(10L, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    private val weatherRepository = WeatherRepositoryImpl(weatherApi)

    val weatherByIdUseCase: GetWeatherByIdUseCase
        get() = GetWeatherByIdUseCase(weatherRepository)

    val weatherByNameUseCase: GetWeatherByNameUseCase
        get() = GetWeatherByNameUseCase(weatherRepository)

    val weatherListUseCase: GetWeatherListUseCase
        get() = GetWeatherListUseCase(weatherRepository)

    var androidResourceProvider: AndroidResourceProvider? = null

    val locationUseCase: GetLocationUseCase
        get() = GetLocationUseCase(locationRepository)

    private var locationProviderClient: FusedLocationProviderClient? = null


    private var locationDataSource: LocationDataSource? = null

    private var locationRepository = LocationRepositoryImpl(locationDataSource)

    fun provideResources(context: Context) {
        androidResourceProvider = AndroidResourceProvider(context)
    }

    fun provideLocationDataSource(
        applicationContext: Context
    ) {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        locationProviderClient?.let {
            locationDataSource = LocationDataSource(it)
        }
        locationRepository = LocationRepositoryImpl(locationDataSource)
    }
}
