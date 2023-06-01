package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherByIdUseCaseTest {

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherByIdUseCase = GetWeatherByIdUseCase(weatherRepository)
    }

    @Test
    fun whenGetWeatherByIdUseCaseExpectedSuccess() {
        // arrange
        val requestId = 0
        val expectedData: WeatherInfo = mockk {
            every { humidity } returns 100
            every { main } returns "main"
            every { windDeg } returns 100
        }
        coEvery {
            weatherRepository.getWeatherById(requestId)
        } returns expectedData
        // act
        runTest {
            val result = getWeatherByIdUseCase.invoke(id = requestId)

            // assert
            assertEquals(expectedData, result)
            assertEquals(expectedData.humidity, result.humidity)
            assertEquals(expectedData.main, result.main)
            assertEquals(expectedData.windDeg, result.windDeg)
        }
    }

    @Test
    fun whenGetWeatherByIdUseCaseExpectedError() {
        // arrange
        val requestId = 0
        coEvery {
            weatherRepository.getWeatherById(requestId)
        } throws RuntimeException()
        // act
        runTest {
            // assert
            assertFailsWith<RuntimeException> {
                getWeatherByIdUseCase.invoke(id = requestId)
            }
        }
    }
}
