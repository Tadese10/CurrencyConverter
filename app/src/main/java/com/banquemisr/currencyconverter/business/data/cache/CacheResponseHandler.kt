package com.banquemisr.currencyconverter.business.data.cache

import android.view.View
import com.banquemisr.currencyconverter.business.domain.state.DataState
import com.banquemisr.currencyconverter.business.domain.state.StateEvent
import com.banquemisr.currencyconverter.business.data.cache.CacheErrors.CACHE_DATA_NULL
import com.banquemisr.currencyconverter.business.domain.state.MessageType
import com.banquemisr.currencyconverter.business.domain.state.Response
import com.banquemisr.currencyconverter.business.domain.state.UIComponentType

abstract class CacheResponseHandler<ViewState, Data>(
    private val response: CacheResult<Data?>,
    private val stateEvent: StateEvent?
){
    suspend fun getResult(): DataState<ViewState>?{
        return when(response){
            is CacheResult.GenericError ->{
                DataState.error(
                    response = Response(
                        message = "${stateEvent?.errorInfo()}\n\n Reason:${response.errorMessage} ",
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent =  stateEvent
                )
            }
            is CacheResult.Success ->{
                if(response.value == null){
                    DataState.error(
                        response = Response(
                            message = "${stateEvent?.errorInfo()}\n\n Reason: ${CACHE_DATA_NULL} ",
                            uiComponentType = UIComponentType.Dialog(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent =  stateEvent
                    )
                }else{
                    handleSuccess(resultObject = response.value)
                }
            }
        }

    }

    abstract fun handleSuccess(resultObject: Data): DataState<ViewState>
}