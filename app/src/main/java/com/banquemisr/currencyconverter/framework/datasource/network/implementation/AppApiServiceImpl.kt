package com.banquemisr.currencyconverter.framework.datasource.network.implementation

import com.banquemisr.currencyconverter.framework.datasource.network.abstraction.AppApiService
import com.banquemisr.currencyconverter.framework.datasource.network.mappers.NetworkMapper
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiServiceImpl
@Inject
constructor(
    private val networkMapper : NetworkMapper
): AppApiService {


}