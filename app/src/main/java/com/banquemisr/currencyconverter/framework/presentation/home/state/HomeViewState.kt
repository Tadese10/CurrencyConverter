package com.banquemisr.currencyconverter.framework.presentation.home.state

import android.os.Parcelable
import com.banquemisr.currencyconverter.business.domain.state.ViewState
import com.banquemisr.currencyconverter.framework.datasource.network.model.CurrencyConverterResponse
import com.banquemisr.currencyconverter.framework.datasource.network.model.SymbolResponse
import kotlinx.android.parcel.Parcelize


@Parcelize
data class HomeViewState constructor(
    var Symbols : SymbolResponse? = null,
    var layoutManagerState: Parcelable? = null,
    var CurrencyResponse: CurrencyConverterResponse? = null
): Parcelable, ViewState {

    override fun toString(): String {
        return "HomeViewState"
        //return "HomeViewState(Posts=$Posts, newComment=$newComment, page=$page, layoutManagerState=$layoutManagerState)"
    }
}