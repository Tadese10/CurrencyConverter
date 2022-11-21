package com.banquemisr.currencyconverter.di

import com.banquemisr.currencyconverter.framework.presentation.BaseApplication
import com.banquemisr.currencyconverter.framework.presentation.MainActivity
import com.banquemisr.currencyconverter.framework.presentation.home.MainFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
@Component(
    modules = [
        AppModule::class,
        ProductionModule::class,
        AppViewModelModule::class,
        AppFragmentFactoryModule::class
    ]
)
interface  AppComponent{

    @Component.Factory//You can also use @Component.Builder
    interface Factory{
        fun create(@BindsInstance app: BaseApplication): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)
}