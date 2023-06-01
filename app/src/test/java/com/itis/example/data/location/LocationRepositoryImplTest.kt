package com.itis.example.data.location

import com.itis.example.data.location.datasource.LocationDataSource
import com.itis.example.domain.location.model.LocationModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class LocationRepositoryImplTest {

    @MockK
    lateinit var locationDataSource: LocationDataSource

    private lateinit var locationRepositoryImpl: LocationRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        locationRepositoryImpl = LocationRepositoryImpl(locationDataSource)
    }

    @Test
    fun whenGetLocationExpectedSuccess() {
        // arrange
        val expectedLocation = LocationModel(
            latitude = 0.0,
            longitude = 0.0
        )
        coEvery {
            locationDataSource.getLastLocation()
        } returns expectedLocation
        // act
        runTest {
            val result = locationRepositoryImpl.getLocation()
            // assert
            assertEquals(expectedLocation, result)
            assertEquals(expectedLocation.latitude, result?.latitude)
            assertEquals(expectedLocation.longitude, result?.longitude)
        }
    }

    @Test
    fun whenGetLocationExpectedError() {
        // arrange
        coEvery {
            locationDataSource.getLastLocation()
        } throws Throwable()
        // act
        runTest {
            // assert
            assertFailsWith<Throwable> {
                locationRepositoryImpl.getLocation()
            }
        }
    }
}
