package com.enciyo.flippermocknetworkplugin

import com.enciyo.flippermocknetworkplugin.model.Mock
import com.enciyo.flippermocknetworkplugin.model.Config
import com.enciyo.flippermocknetworkplugin.model.isSameMock
import com.enciyo.flippermocknetworkplugin.model.isSameEndpoint
import com.enciyo.flippermocknetworkplugin.queue.QueueExecutor
import com.enciyo.flippermocknetworkplugin.queue.QueueExecutorImpl
import java.lang.IllegalStateException


internal class MockManagement(
    private val queueExecutor: QueueExecutor = QueueExecutorImpl(),
    private val currentList: ArrayList<Mock> = arrayListOf(),
) {

    var config: Config = Config()
        private set

    suspend fun setConfigs(config: Config) = queueExecutor.enqueue {
        this.config = config
    }

    suspend fun addAll(mock: List<Mock>) = queueExecutor.enqueue {
        currentList.addAll(mock)
    }

    suspend fun push(mock: Mock) = queueExecutor.enqueue {
        val existIndex = currentList.indexOfFirst { it.isSameMock(mock) || it.isSameEndpoint(mock) }
        if (existIndex != -1)
            throw IllegalStateException("Mock uniqueId: ${mock.uniqueId} is exist! The same mock cannot be added.")
        currentList.add(mock)
    }

    suspend fun update(mock: Mock) = queueExecutor.enqueue {
        val existIndex = findIndexOfFirstMock(mock)
        if (existIndex == -1)
            throw IllegalStateException("Mock uniqueId: ${mock.uniqueId} is not exist! Mock could not be updated!")
        currentList[existIndex] = mock
    }

    suspend fun remove(mock: Mock) = queueExecutor.enqueue {
        val existIndex = findIndexOfFirstMock(mock)
        if (existIndex == -1)
            throw IllegalStateException("Mock uniqueId: ${mock.uniqueId} is not exist. Mock could not be removed!")
        currentList.removeAt(existIndex)
    }

    fun findFirstOrNullMock(requestMock: Mock): Mock? =
        currentList.firstOrNull { it.isSameMock(requestMock) }

    private fun findIndexOfFirstMock(mock: Mock): Int =
        currentList.indexOfFirst { it.isSameEndpoint(mock) }


    fun clear() {
        currentList.clear()
    }


}