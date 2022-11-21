package com.banquemisr.currencyconverter.business.interactors.home

import com.banquemisr.currencyconverter.business.data.network.ApiResponseHandler
import com.banquemisr.currencyconverter.business.data.network.abstraction.AppNetworkDataSource
import com.banquemisr.currencyconverter.business.data.util.safeApiCall
import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.business.domain.state.MessageType
import com.banquemisr.currencyconverter.business.domain.state.Response
import com.banquemisr.currencyconverter.business.domain.state.UIComponentType
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeStateEvent
import com.banquemisr.currencyconverter.framework.presentation.home.state.HomeViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllSymbols(
    private val networkDataSource: AppNetworkDataSource,
) {
    fun get(stateEvent: HomeStateEvent.GetAllSymbolsEvent): Flow<DataState<HomeViewState>?> =
        flow {

            val networkResult = safeApiCall(Dispatchers.IO) {
                networkDataSource.getAllSymbols()
            }

            val response = object : ApiResponseHandler<HomeViewState, SymbolResponse>(
                response = networkResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: SymbolResponse): DataState<HomeViewState> {
                    val data = HomeViewState(
                        Symbols = resultObj
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