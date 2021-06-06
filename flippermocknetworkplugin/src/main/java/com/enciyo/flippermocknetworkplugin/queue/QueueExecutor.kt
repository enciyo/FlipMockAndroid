package com.enciyo.flippermocknetworkplugin.queue

internal interface QueueExecutor {
    suspend fun <T> enqueue(block: suspend () -> T): T
}