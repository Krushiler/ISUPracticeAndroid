package com.bravedeveloper.data.remote.api

import com.bravedeveloper.data.BuildConfig
import com.bravedeveloper.data.remote.api.util.AuthenticatorInterceptor
import com.bravedeveloper.data.remote.api.util.MoshiConverters
import com.bravedeveloper.data.remote.api.util.RetryAfterInterceptor
import com.bravedeveloper.data.remote.api.websocket.SandBaseSocketListener
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object  SandBaseApi {
    const val BASE_URL: String = "https://s.sandbase.ru/"
    private const val TIMEOUT = 10L

    fun getService(): SandBaseService{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthenticatorInterceptor())
            .addInterceptor(RetryAfterInterceptor())

        val client = httpClient.build()

        client.newWebSocket(Request.Builder().url(BASE_URL).build(), SandBaseSocketListener())

        val moshi = Moshi.Builder()
            .add(Wrapped.ADAPTER_FACTORY)
            .add(MoshiConverters())
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build().create(SandBaseService::class.java)
    }

}