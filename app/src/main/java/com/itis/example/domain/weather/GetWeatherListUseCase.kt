package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherUIModel
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        cnt: Int
    ): List<WeatherUIModel> = weatherRepository.getSomeCities(lat, lon, cnt)
}
