package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo

class GetWeatherByIdUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        id: Int
    ): WeatherInfo = weatherRepository.getWeatherById(id)
}
