package com.itis.example.di

import com.itis.example.data.location.LocationRepositoryImpl
import com.itis.example.domain.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    fun provideLocationRepository(
       locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
