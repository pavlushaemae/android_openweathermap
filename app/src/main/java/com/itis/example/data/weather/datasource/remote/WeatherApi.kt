package com.itis.example.data.weather.datasource.remote

import com.itis.example.data.weather.datasource.remote.response.CitiesResponse
import com.itis.example.data.weather.datasource.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeather(
        @Query("id") cityId: Int,
    ): WeatherResponse

    @GET("find")
    suspend fun getSomeWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") countOfCities: Int
    ): CitiesResponse

}

