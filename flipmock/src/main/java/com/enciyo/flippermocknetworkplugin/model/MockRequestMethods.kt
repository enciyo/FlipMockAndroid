package com.enciyo.flippermocknetworkplugin.model

import androidx.annotation.Keep
import java.lang.Exception

/**
 * @since created on 21.05.2021
 * @author mustafa.kilic
 */

@Keep
internal enum class MockRequestMethods(val key:String) {
    POST("Post"),
    GET("Get"),
    PUT("Put"),
    Update("Update"),
    DELETE("Delete");

    companion object {
        fun safeValueOf(value: String, defaultValue: MockRequestMethods? = null) =
            try {
                valueOf(value)
            } catch (e: Exception) {
                defaultValue
            }
    }
}