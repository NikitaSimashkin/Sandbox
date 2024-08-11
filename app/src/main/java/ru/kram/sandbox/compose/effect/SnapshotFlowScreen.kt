package ru.kram.sandbox.compose.effect

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@Composable
fun SnapshotFlowScreen() {
    val counter = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = {
                counter.intValue--
            }
        ) {
            Text(text = "Decrement")
        }

        Button(
            onClick = {
                counter.intValue = counter.intValue
            }
        ) {
            Text(text = "Current Value")
        }

        Button(
            onClick = {
                counter.intValue++
            }
        ) {
            Text(text = "Increment")
        }

        Text(text = "Counter: ${counter.intValue}")
    }

    LaunchedEffect(Unit) {
        snapshotFlow { counter.intValue }
            .onEach {
                Log.d("SnapshotFlow", "flow: $it")
            }
            .onCompletion {
                Log.d("SnapshotFlow", "flow: Completed")
            }
            .launchIn(this)

    }
}