package ru.kram.sandbox

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

class VolatileTest {

    @Volatile
    private var counter = 0

    private val counter2 = AtomicInteger(0)

    @Test
    fun testVolatile() = runBlocking {
        val list = (0 until 1000).map {
            launch(Dispatchers.IO) {
                repeat(100) {
                    counter++
                }
            }
        }

        list.joinAll()

        println(counter)
    }

    @Test
    fun testAtomic() = runBlocking {
        val list = (0 until 1000).map {
            launch(Dispatchers.IO) {
                repeat(100) {
                    synchronized(this@VolatileTest) {
                        counter2.incrementAndGet()
                    }
                }
            }
        }

        list.joinAll()

        println(counter2.get())
    }

    @Test
    fun testMutableStateFlow() {
        val flow = MutableStateFlow(0)

        flow.update { old ->
            println("first")

            flow.update { old ->
                println("second")
                old + 1
            }

            old + 1
        }

        println(flow.value)
    }
}