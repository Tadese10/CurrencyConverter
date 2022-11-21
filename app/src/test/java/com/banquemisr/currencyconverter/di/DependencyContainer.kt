package com.banquemisr.currencyconverter.di

import com.banquemisr.currencyconverter.business.data.AppDataFactory
import com.banquemisr.currencyconverter.business.data.cache.FakeAppCacheDataSourceImpl
import com.banquemisr.currencyconverter.business.data.network.FakeAppNetworkDataSourceImpl
import com.banquemisr.currencyconverter.util.isUnitTest

class DependencyContainer {
    lateinit var appDataFactory: AppDataFactory
    lateinit var appCacheDataSource: FakeAppCacheDataSourceImpl
    lateinit var appNetworkDatasource: FakeAppNetworkDataSourceImpl
    var loadSymbolsData : Boolean = false

    init {
        isUnitTest = true
    }

    fun build() {
        this.javaClass.classLoader?.let{classLoader ->
            appDataFactory = AppDataFactory(classLoader)
        }
        appNetworkDatasource = FakeAppNetworkDataSourceImpl(
            symbols = appDataFactory.produceSymbols(),
            histories = appDataFactory.produceHistories(),
            popularHistories = appDataFactory.producePopularHistories()
        )

        appCacheDataSource = FakeAppCacheDataSourceImpl(

        )
    }


}