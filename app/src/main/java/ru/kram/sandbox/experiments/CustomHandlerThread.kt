package ru.kram.sandbox.experiments

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.StrictMode
import java.util.Queue
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

class CustomHandlerThread(name: String) : Thread(name) {

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

class TaskQueueThread : Thread() {

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