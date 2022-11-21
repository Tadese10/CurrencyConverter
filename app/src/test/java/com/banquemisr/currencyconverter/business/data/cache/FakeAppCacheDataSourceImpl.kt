package com.banquemisr.currencyconverter.business.data.cache

import com.banquemisr.currencyconverter.business.data.cache.abstraction.AppCacheDatasource

class FakeAppCacheDataSourceImpl
constructor(
    var throwSQLiteError : Boolean = false
) : AppCacheDatasource {


    companion object{
        const val FORCE_NEW_TODO_EXCEPTION = -1
        const val FORCE_GENERAL_FAILURE = -2
        const val FORCE_TODO_SEARCH_GENERAL_EXCEPTION = "FORCE_TODO_SEARCH_GENERAL_EXCEPTION"
    }
}