package com.example.kotlinpractice.network


import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class NetworkRequest {
    suspend inline fun <reified T : Any> apiRequest(
        crossinline call: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return try {
            val response = withContext(Dispatchers.IO) {
                call.invoke()
            }
            withContext(Dispatchers.Main) {
                when (response.code()) {


                    in (200..299) -> NetworkResponse.Success(response.body())

                    else -> {

                        val errorModel = try {
                            val errorJsonString = response.errorBody()?.string()
                                ?: "Something went wrong"

                            Gson().fromJson(
                                JsonParser.parseString(errorJsonString),
                                T::class.java
                            )

                        } catch (exception: Exception) {

                            val jsonObject = JsonObject()
                            jsonObject.addProperty(
                                "message",
                                exception.message
                            )

                            Gson().fromJson(jsonObject, T::class.java)
                        }

                        NetworkResponse.Error(
                            errorModel, response.code()
                        )
                    }
                }
            }
        } catch (exception: Throwable) {
            withContext(Dispatchers.Main) {
                NetworkResponse.UnknownError(exception)
            }
        }
    }
}