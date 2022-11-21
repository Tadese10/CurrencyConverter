package com.banquemisr.currencyconverter.framework.datasource.network.abstraction

import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import retrofit2.Response
import retrofit2.http.Query


interface AppNetworkService {

    suspend fun getAllSymbols(): SymbolResponse?

    suspend fun convertCurrency(ToCurrency: String, fromCurrency: String, Amount: String): CurrencyConverterResponse?

    suspend fun getHistories( start_date: String, end_date: String, base: String,symbols: String): Histories?
}