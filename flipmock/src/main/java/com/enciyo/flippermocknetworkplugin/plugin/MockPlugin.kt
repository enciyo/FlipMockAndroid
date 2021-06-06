package com.enciyo.flippermocknetworkplugin.plugin

import android.util.Log
import com.enciyo.flippermocknetworkplugin.MockManagement
import com.enciyo.flippermocknetworkplugin.interceptor.FlipMockInterceptor
import com.enciyo.flippermocknetworkplugin.interceptor.MockInterceptor
import com.enciyo.flippermocknetworkplugin.mapConfig
import com.enciyo.flippermocknetworkplugin.mapMock
import com.facebook.flipper.core.FlipperConnection
import com.facebook.flipper.core.FlipperObject
import com.facebook.flipper.core.FlipperResponder
import kotlinx.coroutines.*
import java.lang.IllegalStateException
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KSuspendFunction2


/**
 * @since created on 5/11/21
 * @author mustafa.kilic
 */

internal class MockPlugin constructor(
    private val mockManagement: MockManagement = MockManagement(),
    override val interceptor: FlipMockInterceptor = MockInterceptor(mockManagement),
) : FlipMockPlugin, CoroutineScope {

    companion object {
        private const val RECEIVE_METHOD_ADD = "Add"
        private const val RECEIVE_METHOD_REMOVE = "Remove"
        private const val RECEIVE_METHOD_UPDATE = "Update"
        private const val RECEIVE_METHOD_CONFIG = "Config"
        private const val RECEIVE_METHOD_ADD_ALL = "AddAll"
    }

    private lateinit var job: Job

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(FlipMockPlugin.TAG, exception.message.toString())
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var connection: FlipperConnection? = null

    override fun onConnect(connection: FlipperConnection?) {
        this.connection = connection
        this.job = SupervisorJob()
        receiveAll()
    }

    private fun receiveAll(){
        connection.receiveWithScope(RECEIVE_METHOD_ADD, ::onReceiveAdd)
        connection.receiveWithScope(RECEIVE_METHOD_REMOVE, ::onReceiveRemove)
        connection.receiveWithScope(RECEIVE_METHOD_UPDATE, ::onReceiveUpdate)
        connection.receiveWithScope(RECEIVE_METHOD_CONFIG, ::onReceiveConfig)
        connection.receiveWithScope(RECEIVE_METHOD_ADD_ALL, ::onReceiveAddAll)
    }

    private suspend fun onReceiveAdd(params: FlipperObject, responder: FlipperResponder) {
        mockManagement.push(params.mapMock())
    }

    private suspend fun onReceiveRemove(params: FlipperObject, responder: FlipperResponder) {
        mockManagement.remove(params.mapMock())
    }

    private suspend fun onReceiveUpdate(params: FlipperObject, responder: FlipperResponder) {
        mockManagement.update(params.mapMock())
    }

    private suspend fun onReceiveConfig(params: FlipperObject, responder: FlipperResponder) {
        mockManagement.setConfigs(params.mapConfig())
    }

    private suspend fun onReceiveAddAll(params: FlipperObject, responder: FlipperResponder) {
        mockManagement.addAll(params.getArray(RECEIVE_METHOD_ADD_ALL).mapMock())
    }

    override fun onDisconnect() {
        mockManagement.clear()
        connection = null
        this.job.cancel()
    }

    private fun FlipperConnection?.receiveWithScope(
        methodName: String,
        onReceive: KSuspendFunction2<FlipperObject, FlipperResponder,Any>,
    ) {
        this?.receive(methodName) { params, responder ->
            launch(exceptionHandler) {
                Log.i(FlipMockPlugin.TAG,"received=> $params ")
                onReceive.invoke(params,responder)
                Log.i(FlipMockPlugin.TAG,"state=> ${mockManagement.currentList} ")
            }
        }
    }

    override fun runInBackground(): Boolean = FlipMockPlugin.RUN_IN_BACKGROUND
    override fun getId(): String = FlipMockPlugin.PLUGIN_ID


}

