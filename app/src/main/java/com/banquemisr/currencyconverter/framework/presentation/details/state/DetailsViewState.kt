package com.banquemisr.currencyconverter.framework.presentation.details.state

import android.os.Parcelable
import com.banquemisr.currencyconverter.business.domain.state.ViewState
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DetailsViewState constructor(
    var Histories : Histories? = null,
    var layoutManagerState: Parcelable? = null,
): Parcelable, ViewState {

    override fun toString(): String {
        return "DetailsViewState"
    }
}