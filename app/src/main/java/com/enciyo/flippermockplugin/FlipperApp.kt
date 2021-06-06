package com.enciyo.flippermockplugin

import android.app.Application
import com.enciyo.flippermocknetworkplugin.plugin.FlipMockPlugin
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

/**
 * @since created on 5/11/21
 * @author mustafa.kilic
 */

class FlipperApp : Application() {

    companion object {
        val flipMockPlugin = FlipMockPlugin.getInstance()
        val networkPlugin = NetworkFlipperPlugin()
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        val client = AndroidFlipperClient.getInstance(this)
        client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
        client.addPlugin(flipMockPlugin)
        client.addPlugin(networkPlugin)
        client.start()
    }

}