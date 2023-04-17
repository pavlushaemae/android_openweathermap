package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.domain.weather.model.WeatherUIModel
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeatherById(id: Int): Single<WeatherInfo>
    fun getWeatherByName(name: String): Single<WeatherInfo>
    fun getSomeCities(lat: Double, lon: Double, cnt: Int): Single<List<WeatherUIModel>>
}
