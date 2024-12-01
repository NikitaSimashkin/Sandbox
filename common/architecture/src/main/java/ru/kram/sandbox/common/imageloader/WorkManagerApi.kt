package ru.kram.sandbox.common.imageloader

import android.os.Bundle

/**
 * Функциональные требования:
 * 1) Выполнение задач в фоне, разово или периодически
 * 2) Отмена задач
 * 3) Статус задачи
 * 4) Передача данных в задачу
 * 5) Получение результата
 * 6) Если задача выполнялась и прервалась, то при следующем запуске она должна продолжиться с того места, где остановилась
 */

interface StepScope {
    fun step(name: String, block: () -> Unit)
}

abstract class Task {

    fun StepScope.doWork(data: Bundle) {
        step("loadImage") {
            // load()
        }

        step("saveImage") {
            // "save"
        }
    }
}

sealed interface Period {
    data object OneTime: Period
    data class EveryMs(val value: Long): Period
}

sealed interface ReschedulePolicy

sealed interface Conditional {
    data object LowBattery: Conditional
    data object WithInternet: Conditional
}

data class TaskParams(
    val id: Long,
    val period: Period,
    val conditionals: List<Conditional>,
    val reschedulePolicy: ReschedulePolicy
)

sealed interface TaskStatus {
    data class Error(val step: String, val exception: Exception)
    data class InProgress(val step: String)
    data object Completed
    data object Cancelled
}

interface WorkManagerApi {
    fun scheduleTask(clazz: Class<out Task>, params: TaskParams, data: Bundle)
    fun getStatus(id: Long): TaskStatus
    fun cancel(id: Long)
    fun getScheduledTasksId(): List<Long>
}

