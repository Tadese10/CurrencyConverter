package com.banquemisr.currencyconverter.business.interactors.home

import com.banquemisr.currencyconverter.business.data.cache.FakeAppCacheDataSourceImpl
import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.di.DependencyContainer
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeStateEvent
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.FlowCollector
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

@InternalCoroutinesApi
class GetAllSymbolsTest {

    /*
            Use Cases
            1. FetchCurrencies_Success_CurrenciesReturned
            2. FetchSymbols_Failed_NothingReturned
     */

    //System in test
    private val _getAllCurrencies : GetAllSymbols

    // dependencies
    private val dependencyContainer: DependencyContainer = DependencyContainer()
    private val todoCacheDataSource: FakeAppCacheDataSourceImpl
    private val appDataSource: AppNetworkDataSource

    init {
        dependencyContainer.build()
        todoCacheDataSource = dependencyContainer.appCacheDataSource
        appDataSource = dependencyContainer.appNetworkDatasource
        _getAllCurrencies = GetAllSymbols(
            networkDataSource = appDataSource,
        )

    }

    @Test
    fun FetchCurrencies_Success_CurrenciesReturned() = runBlocking {

        _getAllCurrencies.get(HomeStateEvent.GetAllSymbolsEvent())
            .collect(object : FlowCollector<DataState<HomeViewState>?>{

                override suspend fun emit(value: DataState<HomeViewState>?) {

                    assertEquals(value?.data?.Symbols?.symbols?.isEmpty(), false)
                }

            })

        assertEquals(appDataSource.getAllSymbols()?.symbols?.isEmpty(), false)
    }

    companion object{

    }

}