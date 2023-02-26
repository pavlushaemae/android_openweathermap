package com.itis.example.data.service.impl

import com.itis.example.data.model.WeatherUIModel
import com.itis.example.data.repo.impl.WeatherRepositoryImpl
import com.itis.example.data.response.WeatherResponse
import com.itis.example.data.service.WeatherService

object WeatherServiceImpl : WeatherService {

    private val weatherRepository = WeatherRepositoryImpl

    override suspend fun getWeatherById(id: Int): WeatherResponse {
        return weatherRepository.getWeatherById(id)
    }

    override suspend fun getWeatherByName(name: String): WeatherResponse {
        return weatherRepository.getWeatherByName(name)
    }


    override suspend fun getSomeCities(lat: Double, lon: Double, cnt: Int): List<WeatherUIModel> {
        return weatherRepository.getSomeCities(lat, lon, cnt)
    }
}
