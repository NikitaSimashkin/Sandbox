package ru.kram.sandbox.features.compose.animation

import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive

@Composable
internal fun TargetBasedAnimationScreen() {

    val animation = remember {
        TargetBasedAnimation( // и еще DecayAnimation
            animationSpec = tween(durationMillis = 1000),
            typeConverter = Float.VectorConverter,
            initialValue = 0f,
            targetValue = 225f
        )
    }

    val rotation = remember { mutableStateOf(0f) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .rotate(rotation.value)
                .background(Color.Red)
                .align(Alignment.TopCenter)
        )
    }
    
    LaunchedEffect(key1 = animation) {
        val startTime = withFrameNanos { it }
        do {
            val playTime = withFrameNanos { it } - startTime
            rotation.value = animation.getValueFromNanos(playTime)
        } while (isActive)
    }
}