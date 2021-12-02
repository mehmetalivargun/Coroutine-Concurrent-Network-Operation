package com.mehmetalivargun.concurrentcoroutineexample.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private var _api: RickAndMortyApi? = null

// Accessing this will crash if done before calling generate()
val api get() = _api!!

const val BASE_URL = "https://rickandmortyapi.com/api/"

fun generateApi(context: Context) {
    if (_api != null) return
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    _api = retrofit.create(RickAndMortyApi::class.java)
}