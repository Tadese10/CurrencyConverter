package com.banquemisr.currencyconverter.business.data.network

import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.business.domain.state.StateEvent
import com.banquemisr.currencyconverter.business.data.network.NetworkErrors.NETWORK_DATA_NULL
import com.banquemisr.currencyconverter.business.data.network.NetworkErrors.NETWORK_ERROR
import com.banquemisr.currencyconverter.business.domain.state.MessageType
import com.banquemisr.currencyconverter.business.domain.state.Response
import com.banquemisr.currencyconverter.business.domain.state.UIComponentType

abstract class ApiResponseHandler<ViewState, Data>
constructor(
    private val response: ApiResult<Data?>,
    private val stateEvent : StateEvent?
){
    suspend fun getResult(): DataState<ViewState>{
        return when(response){
            is ApiResult.GenericError ->{
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\n Reason:${response.errorMessage} ",
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent =  stateEvent
                )
            }
            is ApiResult.NetworkError ->{
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\n Reason:${NETWORK_ERROR} ",
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent =  stateEvent
                )
            }

            is ApiResult.Success -> {
                if(response.value == null){
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\n Reason:${NETWORK_DATA_NULL} ",
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent =  stateEvent
                    )

                }
                else{
                    handleSuccess(response.value)
                }
            }
        }
    }

    abstract suspend fun handleSuccess(resultObj: Data): DataState<ViewState>
}