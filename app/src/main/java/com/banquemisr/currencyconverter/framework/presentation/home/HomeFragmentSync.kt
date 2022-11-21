package com.banquemisr.currencyconverter.framework.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.banquemisr.currencyconverter.business.interactors.home.GetAllSymbols
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeStateEvent
import com.banquemisr.currencyconverter.util.printLogD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeFragmentSync
@Inject
constructor(
    private val sync: GetAllSymbols,
){

    private val _hasSyncBeenExecuted: MutableLiveData<Boolean> = MutableLiveData(false)

    val hasSyncBeenExecuted: LiveData<Boolean>
        get() = _hasSyncBeenExecuted

    fun executeDataSync(coroutineScope: CoroutineScope){
        if(_hasSyncBeenExecuted.value!!){
            return
        }

        val syncJob = coroutineScope.launch {
            launch {
                printLogD("Sync", "downloading Symbols.")
                sync.get(HomeStateEvent.GetAllSymbolsEvent())
            }.join()
        }
        syncJob.invokeOnCompletion {
            CoroutineScope(Main).launch{
                _hasSyncBeenExecuted.value = true
            }
        }
    }

}




















