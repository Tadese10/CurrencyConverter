package com.banquemisr.currencyconverter.framework.datasource.cache.implementation

import com.banquemisr.currencyconverter.business.domain.util.DateUtil
import com.banquemisr.currencyconverter.framework.datasource.cache.abstraction.AppDaoService
import com.banquemisr.currencyconverter.framework.datasource.cache.database.AppDao
import com.banquemisr.currencyconverter.framework.datasource.cache.mappers.CacheMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDaoServiceImpl
@Inject
constructor(
    private val appDao: AppDao,
    private val appMapper: CacheMapper,
    private val dateUtil: DateUtil
): AppDaoService {

}