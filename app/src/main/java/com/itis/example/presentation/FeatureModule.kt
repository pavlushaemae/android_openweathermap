package com.itis.example.presentation

import com.itis.example.presentation.fragment.detail.DetailFragment
import com.itis.example.presentation.fragment.detail.DetailModule
import com.itis.example.presentation.fragment.list.ListFragment
import com.itis.example.presentation.fragment.list.ListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FeatureModule {

    @ContributesAndroidInjector(modules = [ListModule::class])
    fun contributeListFragment(): ListFragment

    @ContributesAndroidInjector(modules = [DetailModule::class])
    fun contributeDetailFragment(): DetailFragment
}
