package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherUIModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherListUseCaseTest {
    @MockK
    lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherListUseCase: GetWeatherListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherListUseCase = GetWeatherListUseCase(weatherRepository)
    }

    @Test
    fun whenGetWeatherListUseCaseExpectedSuccess() {
        // arrange
        val requestLatitude = 0.0
        val requestLongitude = 0.0
        val requestCount = 10
        val expectedData: List<WeatherUIModel> = mockk()
        coEvery {
            weatherRepository.getSomeCities(
                lat = requestLatitude,
                lon = requestLongitude,
                cnt = requestCount
            )
        } returns expectedData
        // act
        runTest {
            val result = getWeatherListUseCase.invoke(
                lat = requestLatitude,
                lon = requestLongitude,
                cnt = requestCount
            )

            // assert
            assertEquals(expectedData, result)
        }
    }

    @Test
    fun whenGetWeatherListUseCaseExpectedError() {
        // arrange
        val requestLatitude = 0.0
        val requestLongitude = 0.0
        val requestCount = 10
        coEvery {
            weatherRepository.getSomeCities(
                lat = requestLatitude,
                lon = requestLongitude,
                cnt = requestCount
            )
        } throws RuntimeException()
        // act
        runTest {
            // assert
            assertFailsWith<RuntimeException> {
                getWeatherListUseCase.invoke(
                    lat = requestLatitude,
                    lon = requestLongitude,
                    cnt = requestCount
                )
            }
        }
    }
}
