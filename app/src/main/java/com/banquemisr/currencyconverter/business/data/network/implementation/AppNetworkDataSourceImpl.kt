package com.banquemisr.currencyconverter.business.data.network.implementation

import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.framework.datasource.network.abstraction.AppApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNetworkDataSourceImpl
@Inject
constructor(
    appService : AppApiService
): AppNetworkDataSource {


}