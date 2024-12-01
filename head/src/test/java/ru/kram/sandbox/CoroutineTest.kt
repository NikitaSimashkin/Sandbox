package ru.kram.sandbox

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test

class CoroutineTest {

    private val scopeWithHandler = CoroutineScope(
        CoroutineExceptionHandler { _, throwable ->
            println("CoroutineExceptionHandler: $throwable")
        } + Dispatchers.IO
    )

    private val scopeWithoutHandler = CoroutineScope(Dispatchers.IO)

    private val scopeWithSupervisor = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val scopeWithHandlerAndSupervisor = CoroutineScope(
        SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            println("CoroutineExceptionHandler: $throwable")
        } + Dispatchers.IO
    )

    init {
        scopeWithoutHandler.coroutineContext[Job]?.invokeOnCompletion {
            println("scopeWithoutHandler invokeOnCompletion")
        }
    }

    @Test
    fun withHandler() = runBlocking {
        val job1 = scopeWithHandler.launch {
            delay(100)
            throw RuntimeException("Job 1 failed")
        }

        val job2 = scopeWithHandler.launch {
            delay(200)
            println("job2 end")
        }

        job1.join()
        job2.join()

        println("before launch third")
        scopeWithHandler.launch {
            println("third launch")
        }.join()
        println("after lauch third")
    }

    @Test
    fun withoutHandler() = runBlocking {
        val job1 = scopeWithoutHandler.launch(CoroutineExceptionHandler {
                _, throwable -> println("CoroutineExceptionHandler2: $throwable")
        }) {
            delay(100)
            throw RuntimeException("Job 1 failed")
        }

        val job2 = scopeWithoutHandler.launch {
            delay(200)
            println("job2 end")
        }

        job1.join()
        job2.join()

        println("before launch third")
        scopeWithoutHandler.launch {
            println("third launch")
        }.join()
        println("after lauch third")
    }

    @Test
    fun withoutHandlerWithSupervisor() = runBlocking {
        val job1 = scopeWithSupervisor.launch {
            delay(100)
            throw RuntimeException("Job 1 failed")
        }

        val job2 = scopeWithSupervisor.launch {
            delay(200)
            println("job2 end")
        }

        job1.join()
        job2.join()

        println("before launch third")
        scopeWithSupervisor.launch {
            println("third launch")
        }.join()
        println("after lauch third")
    }

    @Test
    fun withHandlerWithSupervisor() = runBlocking {
        val job1 = scopeWithHandlerAndSupervisor.launch {
            delay(100)
            throw RuntimeException("Job 1 failed")
        }

        val job2 = scopeWithHandlerAndSupervisor.launch {
            delay(200)
            println("job2 end")
        }

        job1.join()
        job2.join()

        println("before launch third")
        scopeWithHandlerAndSupervisor.launch {
            println("third launch")
        }.join()
        println("after lauch third")
    }

    @Test
    fun innerLaunchWithHandler(): Unit = runBlocking {

        scopeWithoutHandler.launch {

            launch(CoroutineExceptionHandler {
                _, throwable -> println("CoroutineExceptionHandler2: $throwable")
            }) {
                delay(100)
                throw RuntimeException("Job 1 failed")
            }

            launch {
                delay(200)
                println("job2 end")
            }
        }.join()
    }

    @Test
    fun testWithContext(): Unit = runBlocking {
        withContext(SupervisorJob()) {
            launch {
                println("StartDelay")
                delay(1000)
                println("EndDelay")
            }
        }

        println("End")
    }
}