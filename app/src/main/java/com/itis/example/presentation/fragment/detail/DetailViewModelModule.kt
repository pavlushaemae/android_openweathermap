package com.itis.example.presentation.fragment.detail

import androidx.lifecycle.ViewModel
import com.itis.example.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ArgCityId

@Module
interface DetailViewModelModule {

    @Binds
    fun provideArgCityId(@ArgCityId id: Int): Int

    @Binds
    @[IntoMap ViewModelKey(DetailViewModel::class)]
    fun provideViewModel(viewModel: DetailViewModel): ViewModel
}
