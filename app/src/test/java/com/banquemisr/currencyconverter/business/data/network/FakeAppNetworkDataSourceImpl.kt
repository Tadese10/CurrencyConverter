package com.banquemisr.currencyconverter.business.data.network

import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.google.gson.internal.LinkedTreeMap
import java.util.concurrent.ThreadLocalRandom

class FakeAppNetworkDataSourceImpl
constructor(
    private val symbols: SymbolResponse?,
    private val histories: Histories?,
    private val popularHistories: LinkedTreeMap<String, String>,
    var throwGeneralError: Boolean = false
) : AppNetworkDataSource {

    companion object{
        const val SOMETHING_WENT_WRONG = "Something went wrong while processing the request."
        const val FORCE_GENERAL_EXCEPTION = "GENERAL EXCEPTION"
        const val SQLiteError = "SQLiteError"
        const val POST_GENERAL_ERROR = "An error occurred while processing the request."
    }

    override suspend fun getAllSymbols(): SymbolResponse? {
        if(throwGeneralError){
            throw Exception(POST_GENERAL_ERROR)
        }
       return symbols
    }

    override suspend fun convertCurrency(
        ToCurrency: String,
        fromCurrency: String,
        Amount: String
    ): CurrencyConverterResponse? {
        if(throwGeneralError){
            throw Exception(POST_GENERAL_ERROR)
        }
        return CurrencyConverterResponse(success = true, result = ThreadLocalRandom.current().nextDouble(100.3257, 27100.4557))
    }

    override suspend fun getHistories(
        start_date: String,
        end_date: String,
        base: String,
        symbols: String
    ): Histories? {
        if(throwGeneralError){
            throw Exception(POST_GENERAL_ERROR)
        }

        return histories
    }

    override suspend fun getPopularHistories(
        start_date: String,
        end_date: String
    ): LinkedTreeMap<String, String>? {
        if(throwGeneralError){
            throw Exception(POST_GENERAL_ERROR)
        }

        return popularHistories
    }

}