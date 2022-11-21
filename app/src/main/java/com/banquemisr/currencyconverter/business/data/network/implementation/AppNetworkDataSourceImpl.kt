package com.banquemisr.currencyconverter.business.data.network.implementation

import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.framework.datasource.network.abstraction.AppNetworkService
import com.banquemisr.currencyconverter.framework.datasource.network.implementation.AppNetworkServiceImpl
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.google.gson.internal.LinkedTreeMap
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

    override suspend fun getHistories(
        start_date: String,
        end_date: String,
        base: String,
        symbols: String
    ): Histories? = appService.getHistories(start_date, end_date, base, symbols)

    override suspend fun getPopularHistories(start_date: String, end_date: String): LinkedTreeMap<String, String>?  = appService.getPopularHistories(start_date, end_date)
}