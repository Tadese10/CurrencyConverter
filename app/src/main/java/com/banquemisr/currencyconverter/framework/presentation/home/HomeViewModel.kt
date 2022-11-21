package com.banquemisr.currencyconverter.framework.presentation.home

import android.os.Parcelable
import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.business.domain.state.StateEvent
import com.banquemisr.currencyconverter.business.interactors.home.HomeInteractors
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.banquemisr.currencyconverter.framework.presentation.common.BaseViewModel
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeStateEvent
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeViewState
import com.banquemisr.currencyconverter.util.printLogD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class HomeViewModel
    (
    private val homeInteractor: HomeInteractors
): BaseViewModel<HomeViewState>() {

    init {

    }

    fun clearList(){
        printLogD("HomeViewModel2", "clearList")
        val update = getCurrentViewStateOrNew()
        update.Symbols = null
        setViewState(update)
    }

    fun getAllSymbols() {
        setStateEvent(HomeStateEvent.GetAllSymbolsEvent())
        printLogD("HOmeViewModel",
            "loadFirstPage: ${getCurrentViewStateOrNew().Symbols}")
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    override fun handleNewData(data: HomeViewState) {
        data?.let { viewState ->
            viewState.Symbols?.let {
                setSymbols(it)
            }

            viewState.CurrencyResponse?.let {
                setCurrencyResponse(it)
            }
        }


    }

    private fun setCurrencyResponse(it: CurrencyConverterResponse) {
        val update = getCurrentViewStateOrNew()
        update.CurrencyResponse = it
        setViewState(update)
    }

    fun setSymbols(it: SymbolResponse) {
        val update = getCurrentViewStateOrNew()
        update.Symbols = it
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<HomeViewState>?> = when(stateEvent){

            is HomeStateEvent.GetAllSymbolsEvent ->{
                homeInteractor.getAllSymbols.get(
                    stateEvent = stateEvent
                )
            }

            is HomeStateEvent.ConvertCurrencyEvent ->{
                homeInteractor.currency.convert(
                    stateEvent = stateEvent
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): HomeViewState {
        return HomeViewState()
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable){
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    fun getSymbols() : SymbolResponse? {
        printLogD("Symbols: ", getCurrentViewStateOrNew().Symbols.toString())
        return getCurrentViewStateOrNew().Symbols
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

}