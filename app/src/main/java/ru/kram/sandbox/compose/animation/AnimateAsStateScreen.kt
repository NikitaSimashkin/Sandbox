package ru.kram.sandbox.compose.animation

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatableAsStateScreen() {

    var clickState by remember { mutableStateOf(true) }
    val animateState by animateFloatAsState(
        targetValue = if (clickState) 1f else 0.1f,
        animationSpec = tween(durationMillis = 10000),
        label = "AnimatableAsStateScreen",
        finishedListener = {
            Log.d("AnimatableAsStateScreen", "Animation finished")
        }
    )

    Log.d("AnimatableAsStateScreen", "state: $animateState")

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .alpha(animateState)
                .background(Color.Red)
                .align(Alignment.TopCenter)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    clickState = !clickState
                }
        )
    }
}