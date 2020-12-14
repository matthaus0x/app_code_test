package com.codetest.network

import com.codetest.network.api.LocationApi
import com.codetest.network.repository.LocationRepository
import com.codetest.network.util.KeyUtil
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val SECONDS_CONNECTION_TIMEOUT = 10L

val networkModule = module {

    single<LocationApi> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://app-code-test.kry.pet/")
            .client(OkHttpClient().newBuilder().connectTimeout(SECONDS_CONNECTION_TIMEOUT, TimeUnit.SECONDS).build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        retrofit.create(LocationApi::class.java)
    }

    single {
        KeyUtil(get())
    }

    single {
        LocationRepository(get(), get())
    }

}