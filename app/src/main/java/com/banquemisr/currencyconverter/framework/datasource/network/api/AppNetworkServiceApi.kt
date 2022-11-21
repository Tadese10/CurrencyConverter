package com.banquemisr.currencyconverter.framework.datasource.network.api

import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import retrofit2.Response
import retrofit2.http.*

 interface AppNetworkServiceApi {

  @GET("fixer/symbols")
  suspend fun getAllSymbols(): Response<SymbolResponse>

  @GET("fixer/convert")
  suspend fun convertCurrency(@Query("to") ToCurrency: String, @Query("from") fromCurrency: String, @Query("amount") Amount: String): Response<CurrencyConverterResponse>

  @GET("fixer/timeseries")
  suspend fun getHistories(@Query("start_date") start_date: String, @Query("end_date") end_date: String, @Query("base") base: String,  @Query("symbols") symbols: String): Response<Histories>

 }