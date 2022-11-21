package com.banquemisr.currencyconverter.framework.presentation.home.state

import com.banquemisr.currencyconverter.business.domain.state.StateEvent

sealed class HomeStateEvent : StateEvent {
    class GetAllSymbolsEvent(
    ) : HomeStateEvent() {

        override fun errorInfo(): String {
            return "Error fetching all symbols."
        }

        override fun eventName(): String {
            return "GetAllSymbolsEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }

    class ConvertCurrencyEvent(
        val fromCurrency: String, val toCurrency: String,val Amount: String
    ) : HomeStateEvent() {

        override fun errorInfo(): String {
            return "Error converting Currency."
        }

        override fun eventName(): String {
            return "ConvertCurrencyEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}