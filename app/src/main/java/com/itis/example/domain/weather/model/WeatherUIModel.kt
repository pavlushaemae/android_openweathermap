package com.itis.example.domain.weather.model

import androidx.annotation.ColorRes

data class WeatherUIModel(
    val id: Int,
    val name: String,
    val icon: String,
    val temp: String,
    @ColorRes val tempColor: Int,
)
