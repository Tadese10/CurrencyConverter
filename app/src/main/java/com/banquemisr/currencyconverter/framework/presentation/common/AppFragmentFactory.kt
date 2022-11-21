package com.banquemisr.currencyconverter.framework.presentation.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.banquemisr.currencyconverter.framework.presentation.home.MainFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class AppFragmentFactory
@Inject
constructor(
        private val viewModelFactory: ViewModelProvider.Factory,
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =

        when (className) {
            MainFragment::class.java.name -> {
                val fragment = MainFragment(viewModelFactory)
                fragment
            }
            else -> {
                super.instantiate(classLoader, className)
            }
        }

}