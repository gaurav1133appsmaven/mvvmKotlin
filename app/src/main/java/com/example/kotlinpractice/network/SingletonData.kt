package com.example.kotlinpractice.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object SingletonData {
    private const val BASE_URL = "https://dog.ceo/api/"

    val networkGateway: ApiEndpoints by lazy {
        getInstance().create(ApiEndpoints::class.java)
    }

    private fun getInstance(): Retrofit {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(logger).build()).build()
    }

}