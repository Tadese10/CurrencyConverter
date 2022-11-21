package com.banquemisr.currencyconverter.framework.datasource.network.model

import android.os.Parcelable
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Histories
constructor(val success: Boolean,
            val rates: LinkedTreeMap<String, LinkedTreeMap<String, String>>? = null
): Parcelable

data class ItemsViewModel(val text: String) {
}