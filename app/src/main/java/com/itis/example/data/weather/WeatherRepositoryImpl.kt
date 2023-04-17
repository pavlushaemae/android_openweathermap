package com.itis.example.data.weather

import com.itis.example.data.weather.datasource.remote.WeatherApi
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.data.weather.mapper.toWeatherInfo
import com.itis.example.data.weather.mapper.toWeatherUIList
import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.domain.weather.WeatherRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override fun getWeatherById(id: Int): Single<WeatherInfo> = api.getWeather(id)
        .map {
            it.toWeatherInfo()
        }
        .subscribeOn(Schedulers.io())

    override fun getWeatherByName(name: String): Single<WeatherInfo> = api.getWeather(name)
        .map {
            it.toWeatherInfo()
        }
        .subscribeOn(Schedulers.io())

    override fun getSomeCities(lat: Double, lon: Double, cnt: Int): Single<List<WeatherUIModel>> =
        api.getSomeWeather(lat, lon, cnt)
            .map {
                it.toWeatherUIList()
            }
            .subscribeOn(Schedulers.io())
}
