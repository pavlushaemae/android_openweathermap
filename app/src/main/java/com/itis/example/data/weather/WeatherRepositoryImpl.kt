package com.itis.example.data.weather

import com.itis.example.data.weather.datasource.remote.WeatherApi
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.data.weather.mapper.toWeatherInfo
import com.itis.example.data.weather.mapper.toWeatherUIList
import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.domain.weather.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi
): WeatherRepository {
    override suspend fun getWeatherById(id: Int): WeatherInfo {
        return api.getWeather(id).toWeatherInfo()
    }

    override suspend fun getWeatherByName(name: String): WeatherInfo {
        return api.getWeather(name).toWeatherInfo()
    }

    override suspend fun getSomeCities(lat: Double, lon: Double, cnt: Int): List<WeatherUIModel> {
        return api.getSomeWeather(lat,lon, cnt).toWeatherUIList()
    }

}
