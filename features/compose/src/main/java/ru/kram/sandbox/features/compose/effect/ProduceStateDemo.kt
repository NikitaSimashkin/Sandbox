package ru.kram.sandbox.features.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.delay

/**
 * Like flow
 */
@Composable
internal fun produceStateDemo(countTo: Int): State<Int> {
    return produceState(initialValue = 0) {
        while (value < countTo) {
            delay(1000)
            value += 1
        }
    }
}