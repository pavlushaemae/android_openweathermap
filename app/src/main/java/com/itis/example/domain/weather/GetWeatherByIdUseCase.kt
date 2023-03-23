package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import javax.inject.Inject

class GetWeatherByIdUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        id: Int
    ): WeatherInfo = weatherRepository.getWeatherById(id)
}
