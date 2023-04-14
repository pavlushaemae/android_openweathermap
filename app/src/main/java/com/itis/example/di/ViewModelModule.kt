package com.itis.example.di

import androidx.lifecycle.ViewModelProvider
import com.itis.example.presentation.fragment.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory
}
