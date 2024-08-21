package ru.kram.sandbox.experiments

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

class CoroutineLatch2(
    private val count: Int
) {

    private val flow = MutableStateFlow(count)

    fun countDown() {
        flow.value--
    }

    suspend fun await() {
        flow.filter { it == 0 }.first()
    }
}