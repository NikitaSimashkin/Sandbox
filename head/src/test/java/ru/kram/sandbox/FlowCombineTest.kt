package ru.kram.sandbox

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class FlowCombineTest {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + CoroutineName("FlowCombineTest"))

    @Test
    fun combineWithoutOnStart(): Unit = runBlocking {
        val flow1 = flow {
            delay(3000)
            emit(101)
            delay(3000)
            emit(102)
        }
        val flow2 = flow {
            delay(4000)
            emit(201)
        }
        val flow3 = flow {
            delay(5000)
            emit(301)
        }

        combine(
            flow1,
            flow2,
            flow3
        ) { a, b, c ->
            println("a: $a, b: $b, c: $c")
        }.launchIn(this)
    }

    @Test
    fun combineWithOnStart(): Unit = runBlocking {
        val flow1 = flow {
            delay(3000)
            emit(11)
            delay(3000)
            emit(12)
            delay(3000)
            emit(13)
        }
        val flow2 = flow {
            delay(4000)
            emit(21)
            delay(4000)
            emit(22)
            delay(4000)
            emit(23)
        }
        val flow3 = flow {
            delay(5000)
            emit(31)
            delay(5000)
            emit(32)
            delay(5000)
            emit(33)
        }

        combine(
            flow1.onStart { emit(1) },
            flow2.onStart { emit(2) },
            flow3.onStart { emit(3) }
        ) { a, b, c ->
            println("a: $a, b: $b, c: $c")
        }.launchIn(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun flatMapLatestWithBuffer(): Unit = runBlocking {
        val flow1 = flow {
            emit(11)
            emit(12)
            emit(13)
        }.buffer()
        val flow2 = flow {
            delay(4000)
            emit(21)
            delay(4000)
            emit(22)
            delay(4000)
            emit(23)
        }
        val flow3 = flow {
            delay(5000)
            emit(31)
            delay(5000)
            emit(32)
            delay(5000)
            emit(33)
        }

        combine(
            flow1,
            flow2,
            flow3
        ) { a, b, c ->
            Triple(a, b, c)
        }
            .flatMapLatest { (a, b, c) ->
                flow {
                    emit("$a, $b, $c")
                }
            }
            .onEach { println(it) }
            .launchIn(this)
    }

    @Test
    fun combineBackPressure(): Unit = runBlocking {
        val flow1 = flow {
            emit(11)
            emit(12)
            emit(13)
        }.flowOn(Dispatchers.IO)
        val flow2 = flow {
            emit(21)
            emit(22)
            emit(23)
        }.flowOn(Dispatchers.IO)
        val flow3 = flow {
            emit(31)
            emit(32)
            emit(33)
            emit(34)
        }.flowOn(Dispatchers.IO)

        combine(
            flow1,
            flow2,
            flow3
        ) { a, b, c ->
            Triple(a, b, c)
        }
            .conflate()
            .onEach {
                println(it)
                delay(2000)
                println("end")
            }
            .launchIn(this)
    }

    @Test
    fun testDelayCombine(): Unit = runBlocking {
        val flow1 = flow {
            emit(10)
            emit(20)
            emit(100)
        }.flowOn(Dispatchers.IO)
        val flow2 = flow {
            emit(30)
            emit(40)
            emit(50)
        }.flowOn(Dispatchers.IO)

        combine(flow1, flow2) { a, b ->
            println("start delay $a $b")
            delay(4000)
            println("end delay $a $b")
            a to b
        }.launchIn(this)
    }
}