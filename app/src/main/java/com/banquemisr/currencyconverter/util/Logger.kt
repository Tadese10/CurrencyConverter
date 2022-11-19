package com.banquemisr.currencyconverter.util

import android.util.Log
import  com.banquemisr.currencyconverter.util.Constants.DEBUG
import  com.banquemisr.currencyconverter.util.Constants.TAG

var isUnitTest = false

fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}
