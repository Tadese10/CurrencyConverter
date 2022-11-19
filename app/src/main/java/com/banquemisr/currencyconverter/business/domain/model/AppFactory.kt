package com.banquemisr.currencyconverter.business.domain.model

import com.banquemisr.currencyconverter.business.domain.util.DateUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppFactory
@Inject
constructor(
    private val dateUtil: DateUtil
){

}