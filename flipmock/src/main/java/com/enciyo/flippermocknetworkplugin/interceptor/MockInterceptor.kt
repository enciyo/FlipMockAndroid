package com.enciyo.flippermocknetworkplugin.interceptor

import android.util.Log
import com.enciyo.flippermocknetworkplugin.MockManagement
import com.enciyo.flippermocknetworkplugin.mapMock
import com.enciyo.flippermocknetworkplugin.mapResponseBody
import com.enciyo.flippermocknetworkplugin.model.Mock
import com.enciyo.flippermocknetworkplugin.plugin.FlipMockPlugin
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @since created on 2/2/21
 * @author mustafa.kilic
 */

internal class MockInterceptor(private val management: MockManagement) : FlipMockInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val isMockEnable = management.config.isMockEnable
        val isLoggable = management.config.isLoggable
        if (isMockEnable) {
            val existMock = management.findFirstOrNullMock(request.mapMock())
            if (existMock != null && existMock.isMockEnable) {
                if (isLoggable) log(existMock)
                return existMock.mapResponseBody(request)
            }
        }
        return chain.proceed(request)
    }

    private fun log(existMock: Mock) {
        Log.v(FlipMockPlugin.TAG, "Mocked => ${existMock.uniqueId} : ${existMock.endpoint}")
    }
}



