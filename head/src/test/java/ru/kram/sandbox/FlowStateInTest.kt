package ru.kram.sandbox

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FlowStateInTest {

    @Test
    fun stateInTest() = runBlocking {
        val flow = flow {
            emit(10)
            delay(1000)
            emit(20)
            delay(1000)
            emit(30)
            delay(1000)
        }.onEach {
            println("collect $it")
        }.stateIn(CoroutineScope(Dispatchers.IO))

        val collectScope = CoroutineScope(Dispatchers.IO)
        for (i in 0..3) {
            flow.launchIn(collectScope)
        }

        collectScope.coroutineContext.job.join()
    }
}