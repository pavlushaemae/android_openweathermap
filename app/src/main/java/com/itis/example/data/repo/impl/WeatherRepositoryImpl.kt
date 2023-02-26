package com.itis.example.data.repo.impl

import com.itis.example.R
import com.itis.example.data.DataContainer
import com.itis.example.data.model.WeatherUIModel
import com.itis.example.data.repo.WeatherRepository
import com.itis.example.data.response.WeatherResponse

object WeatherRepositoryImpl : WeatherRepository {

    private val api = DataContainer.weatherApi

    override suspend fun getWeatherById(id: Int): WeatherResponse {
        return api.getWeather(id)
    }

    override suspend fun getWeatherByName(name: String): WeatherResponse {
        return api.getWeather(name)
    }

    override suspend fun getSomeCities(lat: Double, lon: Double, cnt: Int): List<WeatherUIModel> {
        val tenNearestCitiesWeatherUI: MutableList<WeatherUIModel> =
            api.getSomeWeather(lat, lon, cnt).list.map {
                val color = it.main.let { main ->
                    when {
                        main.temp < -20 -> R.color.blue_dark
                        main.temp < -10 -> R.color.blue
                        main.temp < 0 -> R.color.blue_light
                        main.temp == 0.0 -> R.color.green
                        main.temp > 20 -> R.color.orange
                        main.temp > 10 -> R.color.yellow
                        main.temp > 0 -> R.color.yellow_light
                        else -> R.color.black
                    }
                }

                WeatherUIModel(
                    id = it.id,
                    name = it.name,
                    icon = it.weather[0].icon,
                    tempColor = color,
                    temp = it.main.temp.toString()
                )


            }.toMutableList()

        return tenNearestCitiesWeatherUI
    }
}
