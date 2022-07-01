package com.bravedeveloper.data.remote.api.dadata

import com.bravedeveloper.data.remote.api.request.Token
import io.reactivex.exceptions.Exceptions
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DadataTokenInterceptor : Interceptor {

    val dadata_key = "bc5e086df05b71140767774da22f04b1dfe92d9c"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val currentRequest = chain.request()

        val token = "Token ${dadata_key }"

        val newRequest = currentRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", token)
            .build()

        // Error handling in RxJava
        // http://blog.danlew.net/2015/12/08/error-handling-in-rxjava/
        try {
            return chain.proceed(newRequest)
        } catch (e: IOException) {
            // Transform checked Exception in Unchecked Exception
            throw Exceptions.propagate(e)
        }
    }
}