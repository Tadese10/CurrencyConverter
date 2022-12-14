package com.banquemisr.currencyconverter.framework.presentation

import android.app.Application
import com.banquemisr.currencyconverter.di.AppComponent
import com.banquemisr.currencyconverter.di.DaggerAppComponent
import kotlinx.coroutines.*

@FlowPreview
@ExperimentalCoroutinesApi
open class BaseApplication : Application(){

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    open fun initAppComponent(){
        appComponent = DaggerAppComponent
            .factory()
            .create(this)
    }


}