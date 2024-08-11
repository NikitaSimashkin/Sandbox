package ru.kram.sandbox.compose.effect

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kram.sandbox.utils.NoRippleInteractionSource
import kotlin.random.Random

@Composable
fun DerivedStateScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var state by remember { mutableStateOf(DerivedStateScreenState.DerivedState) }
        val inputText = remember { mutableStateOf("") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    inputText.value = ""
                    state = DerivedStateScreenState.NotDerivedState
                },
                interactionSource = NoRippleInteractionSource
            ) {
                Text(text = "Not derived state")
            }
            Button(
                onClick = {
                    inputText.value = ""
                    state = DerivedStateScreenState.DerivedState
                },
                interactionSource = NoRippleInteractionSource
            ) {
                Text(text = "Derived state")
            }
        }

        Text(text = "State = $state")

        Text(text = "Text: ${inputText.value}")

        Button(
            onClick = {
                inputText.value += Random.nextInt(10).toString()
            },
            interactionSource = NoRippleInteractionSource
        ) {
            Text(text = "Update text field")
        }

        MyButton(state, inputText)
    }

}

@Stable
enum class DerivedStateScreenState {
    NotDerivedState,
    DerivedState
}

@Composable
fun MyButton(
    screenState: DerivedStateScreenState,
    currentTextState: State<String>
) {
    SubmitButton(screenState, currentTextState)
}

@Composable
fun SubmitButton(
    screen: DerivedStateScreenState,
    text : State<String>
) {
    val isValid = when(screen) {
        DerivedStateScreenState.DerivedState -> remember {
            derivedStateOf {
                text.value.length > 5
            }
        }.value
        DerivedStateScreenState.NotDerivedState -> remember(text.value) {
            text.value.length > 5
        }
    }

    Log.d("Derived", "SubmitButton calculation $isValid")

    Button(
        onClick = {},
        enabled = isValid
    ) {
        Text(text = "Submit")
    }
}
