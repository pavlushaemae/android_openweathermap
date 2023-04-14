package com.itis.example.domain.location

import com.itis.example.domain.location.model.LocationModel
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): LocationModel? {
        return locationRepository.getLocation()
    }
}
