package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherByNameUseCaseTest {

    @MockK
    lateinit var weatherRepository: WeatherRepository

    private lateinit var getWeatherByNameUseCase: GetWeatherByNameUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getWeatherByNameUseCase = GetWeatherByNameUseCase(weatherRepository)
    }

    @Test
    fun whenGetWeatherByNameExpectedSuccess() {
        // arrange
        val requestName = "Ulyanovsk"
        val expectedData: WeatherInfo = mockk {
            every { humidity } returns 100
            every { main } returns "main"
            every { windDeg } returns 100
        }
        coEvery {
            weatherRepository.getWeatherByName(requestName)
        } returns expectedData
        // act
        runTest {
            val result = getWeatherByNameUseCase.invoke(name = requestName)

            // assert
            assertEquals(expectedData, result)
            assertEquals(expectedData.humidity, result.humidity)
            assertEquals(expectedData.main, result.main)
            assertEquals(expectedData.windDeg, result.windDeg)
        }
    }

    @Test
    fun whenGetWeatherByNameUseCaseExpectedError() {
        // arrange
        val requestName = "Ulyanovsk"
        coEvery {
            weatherRepository.getWeatherByName(requestName)
        } throws RuntimeException()
        // act
        runTest {
            // assert
            assertFailsWith<RuntimeException> {
                getWeatherByNameUseCase.invoke(name = requestName)
            }
        }
    }
}
