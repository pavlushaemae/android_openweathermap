package com.itis.example.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.itis.example.data.location.LocationRepositoryImpl
import com.itis.example.data.location.datasource.LocationDataSource
import com.itis.example.domain.location.LocationRepository
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun provideLocationDataSource(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationDataSource = LocationDataSource(fusedLocationProviderClient)

    @Provides
    fun provideLocationRepository(
        locationDataSource: LocationDataSource
    ): LocationRepository = LocationRepositoryImpl(locationDataSource)
}
