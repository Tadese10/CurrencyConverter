package com.banquemisr.currencyconverter.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.banquemisr.currencyconverter.framework.datasource.preferences.PreferenceKeys
import com.banquemisr.currencyconverter.framework.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@FlowPreview
@Module
object ProductionModule {


    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferences(
        application: BaseApplication
    ): SharedPreferences {
        return application.getSharedPreferences(
            PreferenceKeys.NOTE_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

}