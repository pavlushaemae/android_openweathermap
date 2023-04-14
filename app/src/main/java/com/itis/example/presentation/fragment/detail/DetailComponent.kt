package com.itis.example.presentation.fragment.detail

import com.itis.example.di.FragmentScope
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    fun inject(fragment: DetailFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setCityId(@ArgCityId id: Int): Builder
        fun build(): DetailComponent
    }
}
