package com.banquemisr.currencyconverter.business.data.network.implementation

import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.framework.datasource.network.abstraction.AppNetworkService
import com.banquemisr.currencyconverter.framework.datasource.network.implementation.AppNetworkServiceImpl
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNetworkDataSourceImpl
@Inject
constructor(
   private val appService : AppNetworkService
): AppNetworkDataSource {

    override suspend fun getAllSymbols() = appService.getAllSymbols()

    override suspend fun convertCurrency(
        ToCurrency: String,
        fromCurrency: String,
        Amount: String
    ): CurrencyConverterResponse? = appService.convertCurrency(ToCurrency, fromCurrency, Amount)
}