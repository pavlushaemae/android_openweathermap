package com.itis.example.domain.location

import com.itis.example.domain.location.model.LocationModel

interface LocationRepository {
    suspend fun getLocation(): LocationModel?
}
