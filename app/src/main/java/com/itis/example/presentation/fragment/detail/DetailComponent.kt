package com.itis.example.presentation.fragment.detail

import dagger.BindsInstance
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [DetailViewModelModule::class])
interface DetailComponent {

    fun inject(fragment: DetailFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setCityId(@ArgCityId id: Int): Builder
        fun build(): DetailComponent
    }
}
