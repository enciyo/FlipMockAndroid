package com.enciyo.flippermockplugin

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET


/**
 * @since created on 21.05.2021
 * @author mustafa.kilic
 */
interface Service {

    @GET("mock")
    suspend fun mock1(): ResponseBody

    @GET("mock2")
    suspend fun mock2(): ResponseBody

    companion object {
        val service: Service by lazy {
            Retrofit.Builder()
                .baseUrl("https://www.google.com.tr/")
                .client(OkHttpClient.Builder()
                    .addInterceptor(FlipperApp.flipMockPlugin.interceptor)
                    .build())
                .build()
                .create(Service::class.java)
        }

    }
}