package com.enciyo.flippermocknetworkplugin.queue

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class QueueExecutorImpl : QueueExecutor {
    private val mutex: Mutex = Mutex()

    override suspend fun <T> enqueue(block: suspend () -> T): T {
        mutex.withLock {
            return block()
        }
    }
}