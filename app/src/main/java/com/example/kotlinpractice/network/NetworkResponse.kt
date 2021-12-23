package com.example.kotlinpractice.network

import androidx.annotation.Keep

sealed class NetworkResponse<out T : Any> {

    /**
     * Success Response with Body
     */
    @Keep
    data class Success<out T : Any>(val body: T?) : NetworkResponse<T>()

    /**
     * Failure Response with Error Body
     */
    @Keep
    data class Error<out U : Any>(val body: U?, val code: Int) : NetworkResponse<U>()

    /**
     * For example, json parsing error
     */
    @Keep
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing>()
}