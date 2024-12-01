package ru.kram.sandbox.features.compose.animation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
internal fun UpdateTransitionScreen() {

    var startAnimation by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = startAnimation, label = "")

    val yOffset = transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000) },
        label = ""
    ) { state ->
        if (state) 500f else 0f
    }

    val alpha = transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000) },
        label = ""
    ) { state ->
        if (state) 0.1f else 1f
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(y = yOffset.value.dp)
                .alpha(alpha.value)
                .background(Color.Red)
                .align(Alignment.TopCenter)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    startAnimation = !startAnimation
                }
        )
    }
}