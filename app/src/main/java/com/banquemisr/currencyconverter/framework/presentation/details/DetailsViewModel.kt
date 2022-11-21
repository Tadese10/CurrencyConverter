package com.banquemisr.currencyconverter.framework.presentation.details

import android.os.Parcelable
import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.business.domain.state.StateEvent
import com.banquemisr.currencyconverter.business.interactors.details.DetailsInteractors
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.presentation.common.BaseViewModel
import com.banquemisr.currencyconverter.framework.presentation.details.state.DetailsStateEvent
import com.banquemisr.currencyconverter.framework.presentation.details.state.DetailsViewState
import com.banquemisr.currencyconverter.util.printLogD
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
@FlowPreview
class DetailsViewModel
    (
    private val detailsInteractors: DetailsInteractors
): BaseViewModel<DetailsViewState>() {

    fun clearList(){
        printLogD("DetailsViewModel", "clearList")
        val update = getCurrentViewStateOrNew()
        setViewState(update)
    }

    fun getLayoutManagerState(): Parcelable? {
        return getCurrentViewStateOrNew().layoutManagerState
    }

    override fun handleNewData(data: DetailsViewState) {
        data.let { viewState ->

            viewState.Histories?.let {
                setHistories(it)
            }
            viewState.PopularHistories?.let {
                setPopularHistories(it)
            }
        }
    }

    private fun setPopularHistories(it: LinkedTreeMap<String, String>?) {
        val update = getCurrentViewStateOrNew()
        update.PopularHistories = it
        setViewState(update)
    }

    private fun setHistories(it: Histories) {
        val update = getCurrentViewStateOrNew()
        update.Histories = it
        setViewState(update)
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<DetailsViewState>?> = when(stateEvent){

            is DetailsStateEvent.GetCurrencyConversionHistories ->{
                detailsInteractors.histories.get(
                    stateEvent = stateEvent
                )
            }

            is DetailsStateEvent.GetPopularCurrencyConversionHistories ->{
                detailsInteractors.popularHistories.get(
                    stateEvent = stateEvent
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }

        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): DetailsViewState {
        return DetailsViewState()
    }

    fun setLayoutManagerState(layoutManagerState: Parcelable){
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = layoutManagerState
        setViewState(update)
    }

    fun clearLayoutManagerState() {
        val update = getCurrentViewStateOrNew()
        update.layoutManagerState = null
        setViewState(update)
    }

}