package com.banquemisr.currencyconverter.framework.datasource.network.model

import android.os.Parcelable
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.parcel.Parcelize
import java.util.Dictionary

@Parcelize
data class SymbolResponse
constructor(val success: Boolean,
            val symbols: LinkedTreeMap<String, String>? = null
): Parcelable

@Parcelize
data class CurrencyConverterResponse
constructor(val success: Boolean,
            val result: Double? = null,
): Parcelable