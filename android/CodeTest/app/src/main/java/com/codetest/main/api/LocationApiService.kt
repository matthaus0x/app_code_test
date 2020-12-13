package com.codetest.main.api

import com.codetest.main.model.NewLocation
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.experimental.property.inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface LocationApi {
    @GET
    fun get(@Header("X-Api-Key") apiKey: String, @Url url: String): Observable<JsonObject>
    @POST
    fun post(@Header("X-Api-Key") apiKey: String, @Url url: String, @Body location: NewLocation): Observable<JsonObject>
    @DELETE
    fun delete(@Header("X-Api-Key") apiKey: String, @Url url: String): Observable<JsonObject>
}

class LocationApiService(private val api: LocationApi ) {

    fun get(apiKey: String, url: String, success: (JsonObject) -> Unit, error: (String?) -> Unit) {
        api.get(apiKey, url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    success(it)
                },
                onError = {
                    error(it.message)
                }
            )
    }

    fun post(apiKey: String, url: String, location: NewLocation, success: (JsonObject) -> Unit, error: (String?) -> Unit) {
        api.post(apiKey, url, location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    success(it)
                },
                onError = {
                    error(it.message)
                }
            )
    }

    fun delete(apiKey: String, url: String, success: (JsonObject) -> Unit, error: (String?) -> Unit) {
        api.delete(apiKey, url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    success(it)
                },
                onError = {
                    error(it.message)
                }
            )
    }
}