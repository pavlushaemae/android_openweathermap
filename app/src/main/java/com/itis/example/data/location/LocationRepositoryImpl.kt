package com.itis.example.data.location

import com.itis.example.data.location.datasource.LocationDataSource
import com.itis.example.domain.location.LocationRepository
import com.itis.example.domain.location.model.LocationModel
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource?
): LocationRepository {
    override suspend fun getLocation(): LocationModel? {
        return locationDataSource?.getLastLocation()
    }
}
