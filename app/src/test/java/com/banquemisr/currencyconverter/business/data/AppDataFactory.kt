package com.banquemisr.currencyconverter.business.data

import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken

class AppDataFactory(
    private val testClassLoader: ClassLoader
) {
    fun produceSymbols(): SymbolResponse {
        return Gson()
            .fromJson(
                getDataFromFile("test_symbols.json"),
                object: TypeToken<SymbolResponse>() {}.type
            )
    }

    fun produceHistories(): Histories {
        return Gson()
            .fromJson(
                getDataFromFile("test_histories.json"),
                object: TypeToken<Histories>() {}.type
            )
    }

    fun producePopularHistories(): LinkedTreeMap<String, String> {
        return Gson()
            .fromJson(
                getDataFromFile("test_popular_histories.json"),
                object: TypeToken<Histories>() {}.type
            )
    }

   private fun getDataFromFile(fileName: String): String {
        return testClassLoader.getResource(fileName).readText()
    }

}