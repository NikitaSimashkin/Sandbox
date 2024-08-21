package ru.kram.sandbox.language.producerconsumer

class Consumer(
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