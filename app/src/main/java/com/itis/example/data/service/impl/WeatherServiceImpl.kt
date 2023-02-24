package com.itis.example.data.service.impl

import com.itis.example.data.DataContainer
import com.itis.example.data.response.WeatherResponse
import com.itis.example.data.service.WeatherService

class WeatherServiceImpl : WeatherService {

    private val api = DataContainer.weatherApi

    override suspend fun getWeatherById(id: Int): WeatherResponse {
        return api.getWeather(id)
    }

    override suspend fun getWeatherByName(name: String): WeatherResponse {
        return api.getWeather(name)
    }
}