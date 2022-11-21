package com.banquemisr.currencyconverter.business.data.network.abstraction

import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Response

interface AppNetworkDataSource {

    suspend fun getAllSymbols(): SymbolResponse?

    suspend fun convertCurrency(ToCurrency: String, fromCurrency: String, Amount: String): CurrencyConverterResponse?

    suspend fun getHistories( start_date: String, end_date: String, base: String,symbols: String): Histories?

    suspend fun getPopularHistories( start_date: String, end_date: String): LinkedTreeMap<String, String>??

}