package ru.kram.sandbox.features.experiments

import android.os.Looper
import java.util.concurrent.ArrayBlockingQueue

internal class CustomHandlerThread(name: String) : Thread(name) {

    private var looper: Looper? = null
    private val lock = Object()

    override fun run() {
        Looper.prepare()

        synchronized(lock) {
            looper = Looper.myLooper()
            lock.notifyAll()
        }

        Looper.loop()
    }

    fun getLooper(): Looper {
        synchronized(lock) {
            while (looper == null) {
                lock.wait()
            }
            return looper!!
        }
    }

    fun quitSafely() {
        looper?.quitSafely()
    }
}

internal class TaskQueueThread : Thread() {

    private val taskQueue = ArrayBlockingQueue<() -> Unit>(10)

    override fun run() {
        while (!isInterrupted) {
            try {
                val task = taskQueue.take() // Безопасная блокировка и извлечение задачи
                task.invoke()
            } catch (ignored: InterruptedException) { }
        }
    }

    fun postTask(task: () -> Unit) {
        taskQueue.put(task) // Безопасное добавление задачи в очередь
    }
}