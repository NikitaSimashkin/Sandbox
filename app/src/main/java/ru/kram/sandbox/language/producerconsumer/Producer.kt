package ru.kram.sandbox.language.producerconsumer

class Producer(
    private val messageQueue: MessageQueue
) {

    fun startProduce() {
        val producer = Thread {
            for (i in 1..100) {
                messageQueue.addMessage {
                    println("Produced message $i")
                }
                Thread.sleep(1000)
            }
        }
        producer.start()
    }
}