package com.banquemisr.currencyconverter.framework.presentation.common

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.banquemisr.currencyconverter.business.interactors.details.DetailsInteractors
import com.banquemisr.currencyconverter.business.interactors.home.HomeInteractors
import com.banquemisr.currencyconverter.framework.presentation.details.DetailsViewModel
import com.banquemisr.currencyconverter.framework.presentation.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Singleton


@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
class AppViewModelFactory
@Inject
constructor(
    private val homeInteractor : HomeInteractors,
    private val detailsInteractors: DetailsInteractors,
    private val editor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){

            HomeViewModel::class.java -> {
                HomeViewModel(
                    homeInteractor = homeInteractor
                ) as T
            }

            DetailsViewModel::class.java -> {
                DetailsViewModel(
                    detailsInteractors = detailsInteractors
                ) as T
            }

            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}

