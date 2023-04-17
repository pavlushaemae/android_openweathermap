package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWeatherByIdUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        id: Int
    ): Single<WeatherInfo> = weatherRepository.getWeatherById(id)
}
