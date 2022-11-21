package com.banquemisr.currencyconverter.framework.datasource.network.util

import com.banquemisr.currencyconverter.framework.datasource.network.api.AppNetworkServiceApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


object NetworkRetrofitBuilder{
    val baseUrl = "https://api.apilayer.com/"
    val retrofitBuilder : Retrofit.Builder by lazy {

         val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("apikey", "2O1gUkDt7C6wVIjEz9sQOGvAt6DOslpT")
                chain.proceed(request.build())
            }
            .build()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }

    val API_SERVICE_API : AppNetworkServiceApi by lazy {
        retrofitBuilder.build().create(AppNetworkServiceApi::class.java)
    }

}