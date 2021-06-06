package com.enciyo.flippermocknetworkplugin.model

import androidx.annotation.Keep

/**
 * @since created on 21.05.2021
 * @author mustafa.kilic
 */

@Keep
internal data class Config(
    var isLoggable: Boolean = false,
    var isMockEnable: Boolean = true,
)