package com.itis.example.data.weather.mapper

import com.itis.example.R
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.data.weather.datasource.remote.response.CitiesResponse
import com.itis.example.data.weather.datasource.remote.response.WeatherResponse
import com.itis.example.domain.weather.model.WeatherInfo

fun WeatherResponse.toWeatherInfo(): WeatherInfo = WeatherInfo(
    id = id,
    cityName = name,
    humidity = main.humidity,
    temperature = main.temp,
    main = weather.first().main,
    windSpeed = wind.speed,
    windDeg = wind.deg,
    pressure = main.pressure,
    feelsLike = main.feelsLike,
    seaLevel = main.seaLevel,
    tempMax = main.tempMax,
    tempMin = main.tempMin,
    icon = weather.first().icon,
)

fun CitiesResponse.toWeatherUIList(): List<WeatherUIModel> {
    return list.map {
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
}
