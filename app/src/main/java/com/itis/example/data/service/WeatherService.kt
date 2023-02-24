package com.itis.example.data.service

import com.itis.example.data.response.WeatherResponse

interface WeatherService {
    suspend fun getWeatherById(id: Int) : WeatherResponse
    suspend fun getWeatherByName(name: String) : WeatherResponse
}