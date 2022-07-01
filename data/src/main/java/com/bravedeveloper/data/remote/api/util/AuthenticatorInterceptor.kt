package com.bravedeveloper.data.remote.api.util

import com.bravedeveloper.data.remote.api.request.Token
import io.reactivex.exceptions.Exceptions
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticatorInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val currentRequest = chain.request()

        val token = "Bearer ${Token.token}"

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