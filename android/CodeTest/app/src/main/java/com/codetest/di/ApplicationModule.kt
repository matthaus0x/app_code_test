package com.codetest.di

import com.codetest.main.LocationHelper
import com.codetest.main.api.LocationApi
import com.codetest.main.api.LocationApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {

    single<LocationApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app-code-test.kry.pet/")
            .client(OkHttpClient().newBuilder().build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        retrofit.create(LocationApi::class.java)
    }

    single {
        LocationApiService(get())
    }

    single {
        LocationHelper(get())
    }

}