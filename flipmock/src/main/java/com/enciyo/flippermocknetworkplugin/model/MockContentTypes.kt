package com.enciyo.flippermocknetworkplugin.model

import androidx.annotation.Keep

@Keep
internal sealed class MockContentTypes(val name: String) {
    object JSON : MockContentTypes("application/json")
}