package com.banquemisr.currencyconverter.framework.presentation.details.state

import android.os.Parcelable
import com.banquemisr.currencyconverter.business.domain.state.ViewState
import com.banquemisr.currencyconverter.framework.datasource.network.model.Histories
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DetailsViewState(
    var Histories: Histories? = null,
    var layoutManagerState: Parcelable? = null,
    var PopularHistories: LinkedTreeMap<String, String>? = null,
): Parcelable, ViewState {

    override fun toString(): String {
        return "DetailsViewState"
    }
}