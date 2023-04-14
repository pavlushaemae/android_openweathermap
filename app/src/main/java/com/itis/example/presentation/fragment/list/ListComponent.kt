package com.itis.example.presentation.fragment.list

import com.itis.example.di.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [ListModule::class])
interface ListComponent {

    fun inject(fragment: ListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ListComponent
    }
}
