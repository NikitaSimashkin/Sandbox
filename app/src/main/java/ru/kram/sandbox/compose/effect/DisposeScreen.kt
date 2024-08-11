package ru.kram.sandbox.compose.effect

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kram.sandbox.utils.noButtonColor
import kotlin.random.Random

@Composable
fun DisposeScreen() {
    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }

    Column {
        val outerNumber: MutableState<Int?> = remember { mutableStateOf(null) }
        var disposeScreenState by remember { mutableStateOf(DisposeScreenState.BigRedSquare) }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            when (disposeScreenState) {
                DisposeScreenState.BigRedSquare -> {
                    BigRedSquare()
                }

                DisposeScreenState.SmallYellowCircle -> {
                    SmallYellowCircle(outerNumber)
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    disposeScreenState = DisposeScreenState.BigRedSquare
                },
                colors = noButtonColor
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Red)
                )
            }

            Button(
                onClick = {
                    disposeScreenState = DisposeScreenState.SmallYellowCircle
                },
                colors = noButtonColor
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Yellow)
                )
            }

            Button(
                onClick = {
                    job = if (job == null) {
                        scope.launch {
                            while(isActive) {
                                delay(2000)
                                outerNumber.value = Random.nextInt()
                            }
                        }
                    } else {
                        job?.cancel()
                        null
                    }
                },
                colors = noButtonColor
            ) {
                Text(
                    text = "startOuterFlow",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

enum class DisposeScreenState {
    BigRedSquare,
    SmallYellowCircle
}

@Composable
fun BigRedSquare() {
    LaunchedEffect(Unit) {
        Log.d("Dispose: RedSquare", "LaunchedEffect")
    }

    val number = remember {
        val number = Random.nextInt(1000)
        Log.d("Dispose: RedSquare", "remember $number")
        number
    }

    val textState = remember {
        mutableStateOf("")
    }

    LaunchedEffect(textState.value) {
        delay(2000)
        Log.d("Dispose: Launched", "LaunchedEffect with ${textState.value.count { it == '#' }}")
    }

    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Red)
            .clickable {
                textState.value += "#"
            },
        contentAlignment = Alignment.Center
    ) {
        Text(number.toString(), fontSize = 20.sp)
    }

    DisposableEffect(Unit) {
        Log.d("Dispose: RedSquare", "DisposableEffect")

        onDispose {
            Log.d("Dispose: RedSquare", "onDispose")
        }
    }
}

@Composable
fun SmallYellowCircle(outerNumber: State<Int?>) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("Dispose: YellowCircle", "LaunchedEffect")
    }

    val number = remember {
        val number = Random.nextInt(1000)
        Log.d("Dispose: YellowCircle", "remember $number")
        number
    }

    var isDisposed by remember { mutableStateOf(false) }

    if (isDisposed) {
        Log.d("Dispose: RedSquare", "do after onDispose")
    }

    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(number.toString(), fontSize = 20.sp)
    }

    DisposableEffect(outerNumber.value) {
        Log.d("Dispose: YellowCircle", "DisposableEffect")
        scope.launch {
            delay(500)
            isDisposed = false
        }

        onDispose {
            Log.d("Dispose: YellowCircle", "onDispose")
            isDisposed = true
        }
    }
}