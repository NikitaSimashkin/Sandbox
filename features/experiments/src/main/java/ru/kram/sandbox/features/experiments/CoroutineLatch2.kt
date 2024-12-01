package ru.kram.sandbox.features.experiments

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

internal class CoroutineLatch2(
    private val count: Int
) {

    private val flow = MutableStateFlow(count)

    fun countDown() {
        flow.update { it - 1 }
    }

    suspend fun await() {
        flow.filter { it <= 0 }.first()
    }
}