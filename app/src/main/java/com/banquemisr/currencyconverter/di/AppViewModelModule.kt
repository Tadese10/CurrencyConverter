package com.banquemisr.currencyconverter.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.banquemisr.currencyconverter.business.interactors.home.HomeInteractors
import com.banquemisr.currencyconverter.framework.presentation.common.AppViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@FlowPreview
@Module
object AppViewModelModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideAppViewModelFactory(

        homeInteractor: HomeInteractors,
        editor: SharedPreferences.Editor,
        sharedPreferences: SharedPreferences
    ): ViewModelProvider.Factory{
        return AppViewModelFactory(
            homeInteractor = homeInteractor,
            editor = editor,
            sharedPreferences = sharedPreferences
        )
    }
}