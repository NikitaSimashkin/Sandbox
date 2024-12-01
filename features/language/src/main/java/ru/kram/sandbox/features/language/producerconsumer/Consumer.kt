package ru.kram.sandbox.features.language.producerconsumer

internal class Consumer(
    private val messageQueue: MessageQueue
) {

    fun startConsume() {
        val consumer = Thread {
            while (true) {
                val message = messageQueue.getMessage()
                message.doWork()
            }
        }
        consumer.start()
    }
}