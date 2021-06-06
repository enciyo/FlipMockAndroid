package com.enciyo.flippermocknetworkplugin.model

import androidx.annotation.Keep
import java.lang.Exception

/**
 * @since created on 21.05.2021
 * @author mustafa.kilic
 */

@Keep
internal enum class MockRequestMethods {
    POST,
    GET,
    PUT,
    DELETE, ;

    companion object {
        fun safeValueOf(value: String, defaultValue: MockRequestMethods? = null) =
            try {
                valueOf(value)
            } catch (e: Exception) {
                defaultValue
            }
    }
}