package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.domain.weather.model.WeatherUIModel

interface WeatherRepository {
    suspend fun getWeatherById(id: Int): WeatherInfo
    suspend fun getWeatherByName(name: String): WeatherInfo
    suspend fun getSomeCities(lat: Double, lon: Double, cnt: Int): List<WeatherUIModel>
}
