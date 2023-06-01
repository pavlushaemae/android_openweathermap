package com.itis.example.domain.location

import com.itis.example.domain.location.model.LocationModel
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
class GetLocationUseCaseTest {

    @MockK
    lateinit var locationRepository: LocationRepository

    private lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getLocationUseCase = GetLocationUseCase(locationRepository)
    }

    @Test
    fun whenGetWeatherByIdUseCaseExpectedSuccess() {
        // arrange
        val expectedData: LocationModel = mockk {
            every { latitude } returns 0.0
            every { longitude } returns 0.0
        }
        coEvery {
            locationRepository.getLocation()
        } returns expectedData
        // act
        runTest {
            val result = getLocationUseCase()

            // assert
            assertEquals(expectedData, result)
            assertEquals(expectedData.latitude, result?.latitude)
            assertEquals(expectedData.longitude, result?.longitude)
        }
    }

    @Test
    fun whenGetWeatherByIdUseCaseExpectedError() {
        // arrange
        coEvery {
            locationRepository.getLocation()
        } throws RuntimeException()
        // act
        runTest {
            // assert
            assertFailsWith<RuntimeException> {
                getLocationUseCase()
            }
        }
    }
}
