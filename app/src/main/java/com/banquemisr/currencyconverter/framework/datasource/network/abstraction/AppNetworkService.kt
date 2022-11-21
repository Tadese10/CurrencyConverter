package com.banquemisr.currencyconverter.framework.datasource.network.abstraction

import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import retrofit2.Response


interface AppNetworkService {

    suspend fun getAllSymbols(): SymbolResponse?

    suspend fun convertCurrency(ToCurrency: String, fromCurrency: String, Amount: String): CurrencyConverterResponse?
}