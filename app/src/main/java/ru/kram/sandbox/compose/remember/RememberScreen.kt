package ru.kram.sandbox.compose.remember

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RememberScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var state: RememberState? by rememberSaveable { mutableStateOf(null) }
        var value by remember { mutableFloatStateOf(Float.NaN) }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    state = RememberState.Remember
                }
            ) {
                Text(text = "Remember")
            }

            Button(
                onClick = {
                    state = RememberState.RememberSaveable
                }
            ) {
                Text(text = "RememberSaveable")
            }

            Button(
                onClick = {
                    state = RememberState.RememberUpdated
                }
            ) {
                Text(text = "RememberUpdated")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (value.isNaN()) {
                        value = 0f
                    } else {
                        value++
                    }
                }
            ) {
                Text(text = "Increment")
            }

            Text(text = "Last provided: $value")

            Button(
                onClick = {
                    if (value.isNaN()) {
                        value = 0f
                    } else {
                        value--
                    }
                }
            ) {
                Text(text = "Decrement")
            }
        }

        Text(text = "State: $state")

        when(state) {
            RememberState.Remember -> {
                Remember(value = value)
            }

            RememberState.RememberSaveable -> {
                RememberSaveable(value = value)
            }

            RememberState.RememberUpdated -> {
                RememberUpdated(value = value)
            }

            null -> {
                Text(text = "No state")

            }
        }

        Button(
            onClick = {
                state = null
                value = Float.NaN
            }
        ) {
            Text(text = "Reset")
        }
    }
}

enum class RememberState {
    Remember, RememberSaveable, RememberUpdated
}

@Composable
fun Remember(value: Float) {
    val rememberedValue = remember { value }
    Text(text = "Remembered value: $rememberedValue")
}

@Composable
fun RememberSaveable(value: Float) {
    val rememberedValue = rememberSaveable { value }
    Text(text = "Remembered value: $rememberedValue")
}

@Composable
fun RememberUpdated(value: Float) {
    val rememberedValue by rememberUpdatedState(newValue = value)
    Text(text = "Remembered value: $rememberedValue")
}