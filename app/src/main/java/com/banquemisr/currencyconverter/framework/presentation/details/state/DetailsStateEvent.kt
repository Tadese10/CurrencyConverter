package com.banquemisr.currencyconverter.framework.presentation.details.state

import com.banquemisr.currencyconverter.business.domain.state.StateEvent

sealed class DetailsStateEvent : StateEvent {
    class GetCurrencyConversionHistories(
      val start_date: String, val end_date: String,  val fromCurrency: String, val toCurrency: String
    ) : DetailsStateEvent() {

        override fun errorInfo(): String {
            return "Error fetching all histories."
        }

        override fun eventName(): String {
            return "GetCurrencyConversionHistories"
        }

        override fun shouldDisplayProgressBar() = true
    }
}