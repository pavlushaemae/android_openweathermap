package com.itis.example.domain.location

import com.itis.example.domain.location.model.LocationModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): Single<LocationModel> {
        return locationRepository.getLocation()
    }
}
