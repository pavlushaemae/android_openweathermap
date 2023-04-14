package com.itis.example.presentation.fragment.list

import androidx.lifecycle.ViewModel
import com.itis.example.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ListViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(ListViewModel::class)]
    fun provideViewModel(viewModel: ListViewModel): ViewModel
}
