package com.itis.example.domain.location

import com.itis.example.domain.location.model.LocationModel

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LocationModel? {
        return locationRepository.getLocation()
    }
}
