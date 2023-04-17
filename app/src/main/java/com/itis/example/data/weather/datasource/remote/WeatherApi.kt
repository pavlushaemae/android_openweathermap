package com.itis.example.data.weather.datasource.remote

import com.itis.example.data.weather.datasource.remote.response.CitiesResponse
import com.itis.example.data.weather.datasource.remote.response.WeatherResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeather(
        @Query("q") city: String,
    ): Single<WeatherResponse>

    @GET("weather")
    fun getWeather(
        @Query("id") cityId: Int,
    ): Single<WeatherResponse>

    @GET("find")
    fun getSomeWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") countOfCities: Int
    ): Single<CitiesResponse>

}

