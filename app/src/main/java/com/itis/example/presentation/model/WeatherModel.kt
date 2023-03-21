package com.itis.example.presentation.model

data class WeatherModel(
    val cityName: String,
    val humidity: String,
    val temperature: String,
    val wind: String?,
    val windSpeed: String,
    val pressure: String,
    val tempFeel: String,
    val seaLevel: String,
    val tempMax: String,
    val minTemp: String,
    val icon: String,
)
