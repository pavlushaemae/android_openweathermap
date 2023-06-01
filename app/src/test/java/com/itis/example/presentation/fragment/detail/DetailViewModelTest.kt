package com.itis.example.presentation.fragment.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.itis.example.di.ResourceProvider
import com.itis.example.domain.weather.GetWeatherByIdUseCase
import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.presentation.model.WeatherModel
import com.itis.example.presentation.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @MockK
    lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase

    @MockK
    lateinit var androidResourceProvider: ResourceProvider

    private val cityId: Int = 0

    private lateinit var detailViewModel: DetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailViewModel = DetailViewModel(
            getWeatherByIdUseCase,
            androidResourceProvider,
            cityId
        )
    }

    @Test
    fun whenLoadWeather() {
        // arrange
        val expectedWeatherInfo = mockk<WeatherInfo> {
            every { cityName } returns "Kazan"
        }
        coEvery {
            getWeatherByIdUseCase.invoke(id = cityId)
        } returns expectedWeatherInfo
        val expectedResult = mockk<WeatherModel> {
            every { cityName } returns "Kazan"
        }

        // act
        detailViewModel.loadWeather()

        // assert
        assertEquals(expectedResult, detailViewModel.weather.value)
    }

}
