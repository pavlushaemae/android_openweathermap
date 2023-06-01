package com.itis.example.presentation.fragment.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.itis.example.di.ResourceProvider
import com.itis.example.domain.location.GetLocationUseCase
import com.itis.example.domain.location.model.LocationModel
import com.itis.example.domain.weather.GetWeatherByNameUseCase
import com.itis.example.domain.weather.GetWeatherListUseCase
import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.domain.weather.model.WeatherUIModel
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
class ListViewModelTest {

    @MockK
    lateinit var getWeatherByNameUseCase: GetWeatherByNameUseCase

    @MockK
    lateinit var getWeatherListUseCase: GetWeatherListUseCase

    @MockK
    lateinit var getLocationUseCase: GetLocationUseCase

    @MockK
    lateinit var resourceProvider: ResourceProvider

    private lateinit var viewModel: ListViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ListViewModel(
            getWeatherByNameUseCase = getWeatherByNameUseCase,
            getWeatherListUseCase = getWeatherListUseCase,
            getLocationUseCase = getLocationUseCase,
            androidResourceProvider = resourceProvider
        )
    }

    @Test
    fun `when onLocationPermsIsGranted calls`() {
        // arrange
        val requestBoolean = true
        val expectedData = true
        // act
        viewModel.onLocationPermsIsGranted(requestBoolean)
        // assert
        assertEquals(expectedData, viewModel.perms.value)
    }

    @Test
    fun `when onShouldNavigate calls`() {
        // arrange
        val requestId = 0
        val expectedData = 0
        // act
        viewModel.onShouldNavigate(requestId)
        // assert
        assertEquals(expectedData, viewModel.navigateToDetails.value)
    }

    @Test
    fun `when onNeedLocation calls`() {
        // arrange
        val expectedLocationFromUseCase: LocationModel = mockk {
            every { latitude } returns 55.75
            every { longitude } returns 37.61
        }
        val expectedResult = LocationModel(
            latitude = 55.75,
            longitude = 37.61
        )
        coEvery {
            getLocationUseCase()
        } returns expectedLocationFromUseCase
        // act
        viewModel.onNeedLocation()
        // assert
        assertEquals(expectedResult, viewModel.location.value)
    }

    @Test
    fun `when onNeedList calls`() {
        // arrange
        val requestLatitude = 0.0
        val requestLongitude = 0.0
        val requestCount = 10
        val expectedListFromUseCase: List<WeatherUIModel> = listOf(
            mockk {
                every { name } returns "Kazan"
            }
        )
        val expectedResult:List<WeatherUIModel> = listOf(
            mockk {
                every { name } returns "Kazan"
            }
        )
        coEvery {
            getWeatherListUseCase(requestLatitude, requestLongitude, requestCount)
        } returns expectedListFromUseCase
        // act
        viewModel.onNeedList()
        // assert
        assertEquals(expectedResult, viewModel.weatherUIList.value)
    }

    @Test
    fun `when load click`() {
        // arrange
        val queryString = "query"
        val expectedWeatherByUseCase: WeatherInfo = mockk {
            every { id } returns 0
        }
        val expectedResult: WeatherInfo = mockk {
            every { id } returns 0
        }
        coEvery {
            getWeatherByNameUseCase(queryString)
        } returns expectedWeatherByUseCase
        // act
        viewModel.onLoadClick(queryString)
        // assert
        assertEquals(expectedResult.id, viewModel.navigateToDetails.value)
    }
}
