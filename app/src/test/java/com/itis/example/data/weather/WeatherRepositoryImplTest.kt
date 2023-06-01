package com.itis.example.data.weather

import com.itis.example.R
import com.itis.example.data.weather.datasource.remote.WeatherApi
import com.itis.example.data.weather.datasource.remote.response.CitiesResponse
import com.itis.example.data.weather.datasource.remote.response.WeatherResponse
import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.domain.weather.model.WeatherUIModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {

    @MockK
    lateinit var api: WeatherApi

    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        weatherRepositoryImpl = WeatherRepositoryImpl(api)
    }

    private val expectedWeatherResponse = mockk<WeatherResponse> {
        every { id } returns 1823
        every { name } returns "Kazan"
        every { main } returns mockk {
            every { humidity } returns 4617
            every { temp } returns 0.0
            every { pressure } returns 0
            every { feelsLike } returns 0.0
            every { seaLevel } returns 0
            every { tempMax } returns 0.0
            every { tempMin } returns 0.0
        }
        every { weather } returns listOf(
            mockk {
                every { main } returns ""
                every { icon } returns ""
            }
        )
        every { wind } returns mockk {
            every { speed } returns 0.0
            every { deg } returns 0

        }
    }

    @Test
    fun whenGetWeatherByIdExpectedSuccess() {
        // arrange
        val requestId = 0
        val expectedResult = WeatherInfo(
            id = 1823,
            cityName = "Kazan",
            humidity = 4617,
            temperature = 0.0,
            main = "",
            windSpeed = 0.0,
            windDeg = 0,
            pressure = 0,
            feelsLike = 0.0,
            seaLevel = 0,
            tempMax = 0.0,
            tempMin = 0.0,
            icon = ""
        )
        coEvery {
            api.getWeather(requestId)
        } returns expectedWeatherResponse
        // act
        runTest {
            val result = weatherRepositoryImpl.getWeatherById(requestId)

            // assert
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun whenGetWeatherByIdExpectedError() {
        // arrange
        val requestId = 0
        coEvery {
            api.getWeather(requestId)
        } throws Throwable()
        // act
        runTest {
            // assert
            assertFailsWith<Throwable> {
                weatherRepositoryImpl.getWeatherById(requestId)
            }
        }
    }

    @Test
    fun whenGetWeatherByNameExpectedSuccess() {
        // arrange
        val requestName = "Kazan"
        val expectedResult = WeatherInfo(
            id = 1823,
            cityName = "Kazan",
            humidity = 4617,
            temperature = 0.0,
            main = "",
            windSpeed = 0.0,
            windDeg = 0,
            pressure = 0,
            feelsLike = 0.0,
            seaLevel = 0,
            tempMax = 0.0,
            tempMin = 0.0,
            icon = ""
        )
        coEvery {
            api.getWeather(requestName)
        } returns expectedWeatherResponse
        // act
        runTest {
            val result = weatherRepositoryImpl.getWeatherByName(requestName)

            // assert
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun whenGetWeatherByNameExpectedError() {
        // arrange
        val requestName = "Kazan"
        coEvery {
            api.getWeather(requestName)
        } throws Throwable()
        // act
        runTest {
            // assert
            assertFailsWith<Throwable> {
                weatherRepositoryImpl.getWeatherByName(requestName)
            }
        }
    }

    @Test
    fun whenGetSomeCitiesExpectedSuccess() {
        // arrange
        val requestLatitude = 0.0
        val requestLongitude = 0.0
        val requestCount = 10
        val expectedCitiesResponse: CitiesResponse = mockk {
            every { cod } returns "code"
            every { count } returns 10
            every { list } returns listOf(
                mockk {
                    every { id } returns 0
                    every { name } returns "Kazan"
                    every { weather } returns listOf(
                        mockk {
                            every { icon } returns "icon"
                        }
                    )
                    every { main } returns mockk {
                        every { temp } returns 0.0
                    }
                }

            )
            every { message } returns "message"
        }
        val expectedResult: List<WeatherUIModel> = listOf(
            WeatherUIModel(
                id = 0,
                name = "Kazan",
                icon = "icon",
                temp = "0.0",
                tempColor = R.color.green
            )
        )

        coEvery {
            api.getSomeWeather(
                latitude = requestLatitude,
                longitude = requestLongitude,
                countOfCities = requestCount
            )
        } returns expectedCitiesResponse

        // act
        runTest {
            val result = weatherRepositoryImpl.getSomeCities(
                lat = requestLatitude,
                lon = requestLongitude,
                cnt = requestCount
            )

            // assert
            assertEquals(expectedResult, result)
        }
    }

    @Test
    fun whenGetSomeCitiesExpectedError() {
        // arrange
        val requestLatitude = 0.0
        val requestLongitude = 0.0
        val requestCount = 10

        coEvery {
            api.getSomeWeather(
                latitude = requestLatitude,
                longitude = requestLongitude,
                countOfCities = requestCount
            )
        } throws Throwable()

        // act
        runTest {
            // assert
            assertFailsWith<Throwable> {
                weatherRepositoryImpl.getSomeCities(
                    lat = requestLatitude,
                    lon = requestLongitude,
                    cnt = requestCount
                )
            }
        }
    }
}
