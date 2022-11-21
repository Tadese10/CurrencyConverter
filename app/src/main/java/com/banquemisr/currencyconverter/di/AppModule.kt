package com.banquemisr.currencyconverter.di

import android.content.SharedPreferences
import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.business.data.network.implementation.AppNetworkDataSourceImpl
import com.banquemisr.currencyconverter.business.interactors.home.ConvertCurrency
import com.banquemisr.currencyconverter.business.interactors.home.GetAllSymbols
import com.banquemisr.currencyconverter.business.interactors.home.HomeInteractors
import com.banquemisr.currencyconverter.framework.datasource.cache.database.AppDao
import com.banquemisr.currencyconverter.framework.datasource.cache.implementation.AppDaoServiceImpl
import com.banquemisr.currencyconverter.framework.datasource.network.abstraction.AppNetworkService
import com.banquemisr.currencyconverter.framework.datasource.network.api.AppNetworkServiceApi
import com.banquemisr.currencyconverter.framework.datasource.network.implementation.AppNetworkServiceImpl
import com.banquemisr.currencyconverter.framework.datasource.network.util.NetworkRetrofitBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@FlowPreview
@Module
object AppModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPrefsEditor(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppNetworkService(): AppNetworkServiceApi {
        return NetworkRetrofitBuilder.API_SERVICE_API
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppNetworkServiceImple(
        appNetworkServiceApi: AppNetworkServiceApi
    ): AppNetworkService {
        return AppNetworkServiceImpl(appNetworkServiceApi)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppNetworkDataSource(
        appService : AppNetworkService
    ): AppNetworkDataSource {
        return AppNetworkDataSourceImpl(appService)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideAppCacheDataSource(
        appDao: AppDao
    ): AppDaoServiceImpl {
        return AppDaoServiceImpl(appDao)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideHomeInteractors(
        appNetworkDataSource: AppNetworkDataSource,
    ): HomeInteractors {
        return HomeInteractors(
            getAllSymbols = GetAllSymbols(
                appNetworkDataSource
            ),
            ConvertCurrency(
                appNetworkDataSource
            )
        )
    }

}
