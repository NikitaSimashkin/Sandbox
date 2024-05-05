package ru.kram.sandbox.compose.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun AnimatedVisibilityScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

        var isVisibleRedSquare by remember { mutableStateOf(true) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                isVisibleRedSquare = true
            }) {
                Text(text = "Show")
            }
            Button(onClick = {
                isVisibleRedSquare = false
            }) {
                Text(text = "Hide")
            }
        }

        AnimatedVisibility(
            visible = isVisibleRedSquare,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            val color = transition.animateColor(
                transitionSpec = { tween(durationMillis = 2000) },
                label = ""
            ) {
                if (it == EnterExitState.Visible) Color.Red else Color.Green
            }
            Box(
                modifier = Modifier
                    .padding(32.dp)
                    .animateEnterExit(
                        enter = slideInVertically(animationSpec = tween(durationMillis = 2000)),
                        exit = slideOutHorizontally(animationSpec = tween(durationMillis = 2000))
                    )
                    .height(200.dp)
                    .width(200.dp)
                    .background(color = color.value)
            )
        }

        var state by remember { mutableStateOf<SomeState>(SomeState.FirstState) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                state = SomeState.FirstState
            }) {
                Text(text = "Set first state")
            }
            Button(onClick = {
                state = SomeState.SecondState("Second state text")
            }) {
                Text(text = "Set second state")
            }
        }

        AnimatedVisibility(
            visible = state is SomeState.SecondState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            var cachedState by remember { mutableStateOf<SomeState.SecondState?>(null) }
            if (state is SomeState.SecondState) {
                val secondState = state as SomeState.SecondState
                cachedState = secondState
            }

            if (cachedState != null) {
                SecondStateText(state = cachedState as SomeState.SecondState)
            }
        }
    }
}

@Composable
fun SecondStateText(state: SomeState.SecondState) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = state.text
    )
}

sealed  class SomeState {
    data object FirstState : SomeState()
    data class SecondState(val text: String) : SomeState()
}