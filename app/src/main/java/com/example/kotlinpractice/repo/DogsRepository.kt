package com.example.kotlinpractice.repo

import com.example.kotlinpractice.models.DogModel
import com.example.kotlinpractice.network.NetworkRequest
import com.example.kotlinpractice.network.NetworkResponse
import com.example.kotlinpractice.network.SingletonData

class DogsRepository : NetworkRequest() {

    suspend fun getDogData(): NetworkResponse<DogModel> {
        return apiRequest {
            SingletonData.networkGateway.getRandomDog()
        }
    }
}