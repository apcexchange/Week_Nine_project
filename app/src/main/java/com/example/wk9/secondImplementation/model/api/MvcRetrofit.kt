package com.example.wk9.secondImplementation.model.api

import com.example.wk9.firstImplementation.model.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MvcRetrofit {
    //set up logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //set up retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //connecting our Retrofit to endpoint in interface
    val api: MvcNetworkInterface by lazy {
        retrofit.create(MvcNetworkInterface::class.java)
    }
}