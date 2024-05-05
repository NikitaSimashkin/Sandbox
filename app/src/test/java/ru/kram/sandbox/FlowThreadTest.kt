package ru.kram.sandbox

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowThreadTest {

    @Test
    fun testDispatchersMain(): Unit = runBlocking(Dispatchers.IO) {
        val flow = Utils.getDelayedFlow((0..20).toList(), 1000)
            .onEach {
                delay(0);
                println("Thread = ${Thread.currentThread().name}")
            }
            .conflate()

        flow.onEach {
            delay(2000)
            println("collect $it, ${Thread.currentThread().name}")
        }.launchIn(this)
    }
}