package com.itis.example.presentation.fragment.utils

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject

abstract class BaseFragment(
    fragmentLayout: Int
) : Fragment(fragmentLayout) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.getViewModel(
        noinline ownerProducer: () -> ViewModelStoreOwner = { this },
        noinline extrasProducer: (() -> CreationExtras)? = null,
        noinline factoryProducer: (() -> ViewModelProvider.Factory) = { factory }
    ): Lazy<VM> {
        return viewModels(ownerProducer, extrasProducer, factoryProducer)
    }

}
