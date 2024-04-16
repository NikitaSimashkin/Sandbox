package ru.kram.sandbox.compose.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatableScreen() {

    val startValueOffset = 0.dp
    val endValueOffset = 500.dp

    val animatableValue = remember { Animatable(startValueOffset.value) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(y = animatableValue.value.dp)
                .background(Color.Red)
                .align(Alignment.TopCenter)
        )
    }

    LaunchedEffect(key1 = true) {
        animatableValue.animateTo(
            targetValue = endValueOffset.value,
            animationSpec = tween(delayMillis = 1000, durationMillis = 1000)
        )
    }
}