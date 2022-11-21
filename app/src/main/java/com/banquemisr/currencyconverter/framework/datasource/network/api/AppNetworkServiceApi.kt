package com.banquemisr.currencyconverter.framework.datasource.network.api

import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import retrofit2.Response
import retrofit2.http.*

 interface AppNetworkServiceApi {

  @GET("fixer/symbols")
  suspend fun getAllSymbols(): Response<SymbolResponse>

  @GET("fixer/convert")
  suspend fun convertCurrency(@Query("to") ToCurrency: String, @Query("from") fromCurrency: String, @Query("amount") Amount: String): Response<CurrencyConverterResponse>

}