package com.banquemisr.currencyconverter.framework.datasource.network.implementation

import com.banquemisr.currencyconverter.framework.datasource.network.abstraction.AppNetworkService
import com.banquemisr.currencyconverter.framework.datasource.network.api.AppNetworkServiceApi
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.banquemisr.currencyconverter.util.printLogD
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNetworkServiceImpl
@Inject
constructor(
    private val appNetworkServiceApi: AppNetworkServiceApi
) : AppNetworkService {

    override suspend fun getAllSymbols(): SymbolResponse? {
        var data = appNetworkServiceApi.getAllSymbols()
        printLogD("Response", data.toString())
        if(data.code() == 200){
            printLogD("Response.Data", data.body().toString())
            return data.body()
        }
        return SymbolResponse(false, null)
    }

    override suspend fun convertCurrency(ToCurrency: String, fromCurrency: String, Amount: String): CurrencyConverterResponse? {
        var data = appNetworkServiceApi.convertCurrency(ToCurrency, fromCurrency, Amount)
        printLogD("Response", data.toString())
        if(data.isSuccessful){
            return data.body()
        }
        return CurrencyConverterResponse(false, null)
    }

    override suspend fun getHistories(
        start_date: String,
        end_date: String,
        base: String,
        symbols: String
    ): Histories? {
        var data = appNetworkServiceApi.getHistories(end_date, start_date, base, symbols)
        printLogD("HistoriesResponse", data.toString())
        if(data.isSuccessful){
            printLogD("Histories", data.body().toString())

            return data.body()
        }
        return Histories(false, null)
    }

    override suspend fun getPopularHistories(
        start_date: String,end_date: String
    ): LinkedTreeMap<String, String>? {
        var data = appNetworkServiceApi.getPopularHistories(end_date, start_date,"", "")
        printLogD("PopularHistoriesResponse", data.toString())
        if(data.isSuccessful){
            printLogD("Histories", data.body().toString())

            return data.body()?.rates
        }
        return null
    }

}