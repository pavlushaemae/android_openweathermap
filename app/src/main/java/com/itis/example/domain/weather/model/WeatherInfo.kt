package com.itis.example.domain.weather.model

data class WeatherInfo(
    val id: Int,
    val cityName: String,
    val humidity: Int,
    val temperature: Double,
    val main: String,
    val windSpeed: Double,
    val windDeg: Int,
    val pressure: Int,
    val feelsLike: Double,
    val seaLevel: Int,
    val tempMax: Double,
    val tempMin: Double,
    val icon: String
)
