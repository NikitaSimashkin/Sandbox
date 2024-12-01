package ru.kram.sandbox

import org.junit.jupiter.api.Test
import kotlin.concurrent.thread
import kotlin.jvm.internal.Ref.IntRef

class LocalVarInMultipleThreadsTest {

    @Test
    fun localVarTwoThreads() {
        var localVar = 0

        val write = thread {
            while(true) {
                localVar++
                Thread.sleep(1000)
            }
        }

        val read = thread {
            while(true) {
                println("$localVar")
                Thread.sleep(1000)
            }
        }

        write.join()
        read.join()
    }
}