package com.itis.example.presentation.fragment.list

import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ListViewModelModule::class])
interface ListComponent {

    fun inject(fragment: ListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ListComponent
    }
}
