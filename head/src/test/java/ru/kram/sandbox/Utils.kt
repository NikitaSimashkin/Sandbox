package ru.kram.sandbox

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object Utils {

    fun <T> getDelayedFlow(list: List<T>, delay: Long = 1000L): Flow<T> {
        return flow {
            for (i in list) {
                delay(delay)
                emit(i)
            }
        }
    }
}