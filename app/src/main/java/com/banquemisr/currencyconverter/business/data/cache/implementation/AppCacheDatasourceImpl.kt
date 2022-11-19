package com.banquemisr.currencyconverter.business.data.cache.implementation

import com.banquemisr.currencyconverter.business.data.cache.abstraction.AppCacheDatasource
import com.banquemisr.currencyconverter.framework.datasource.cache.abstraction.AppDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppCacheDatasourceImpl
@Inject
constructor(
    private val appDaoService : AppDaoService
) : AppCacheDatasource {

}
