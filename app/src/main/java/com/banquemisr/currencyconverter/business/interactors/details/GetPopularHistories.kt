package com.banquemisr.currencyconverter.business.interactors.details

import com.banquemisr.currencyconverter.business.data.network.ApiResponseHandler
import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.business.data.util.safeApiCall
import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.business.domain.state.MessageType
import com.banquemisr.currencyconverter.business.domain.state.Response
import com.banquemisr.currencyconverter.business.domain.state.UIComponentType
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.presentation.details.state.DetailsStateEvent
import com.banquemisr.currencyconverter.framework.presentation.details.state.DetailsViewState
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPopularHistories(
    private val networkDataSource: AppNetworkDataSource,
) {
    fun get(stateEvent: DetailsStateEvent.GetPopularCurrencyConversionHistories): Flow<DataState<DetailsViewState>?> =
        flow {

            val networkResult = safeApiCall(Dispatchers.IO) {
                networkDataSource.getPopularHistories(stateEvent.start_date, stateEvent.end_date)
            }

            val response = object : ApiResponseHandler<DetailsViewState, LinkedTreeMap<String, String>?>(
                response = networkResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: LinkedTreeMap<String, String>?): DataState<DetailsViewState> {
                    val data = DetailsViewState(
                        PopularHistories = resultObj
                    )
                    return DataState.data(
                        response = Response(
                            message = SUCCESSFUL,
                            uiComponentType = UIComponentType.None(),
                            messageType = MessageType.Success()
                        ),
                        data = data,
                        stateEvent = stateEvent
                    )

                }

            }.getResult()

            emit(response)
        }


    companion object {
        const val SUCCESSFUL = "The request was successful."
    }
}