package com.example.kotlinpractice.network


import com.example.kotlinpractice.models.DogModel

import retrofit2.Response
import retrofit2.http.GET

interface ApiEndpoints {
    @GET("breeds/image/random")
    suspend fun getRandomDog(): Response<DogModel>
}