package ru.kram.sandbox.language.producerconsumer

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class MessageQueue {
    private val messages = mutableListOf<Message>()

    fun addMessage(message: Message) {
        synchronized(this) {
            messages.add(message)
            notifyAlll()
        }
    }

    fun getMessage(): Message {
        synchronized(this) {
            while (messages.isEmpty()) {
                try {
                    waitt()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
            return messages.removeAt(0)
        }
        ReentrantReadWriteLock().apply {
            read {

            }
            write {

            }
        }
    }

    private fun waitt() {
        (this as Object).wait()
    }

    private fun notifyAlll() {
        (this as Object).notifyAll()
    }
}