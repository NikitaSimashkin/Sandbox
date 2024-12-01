package ru.kram.sandbox.features.compose.animation

import android.util.Log
import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.isActive
import kotlin.math.absoluteValue

@Composable
internal fun DecayAnimationScreen() {
    val screenSize = with(LocalConfiguration.current) {
        with(LocalDensity.current) {
            screenWidthDp.dp.toPx()
        }
    }
    val animation = remember {
        DecayAnimation(
            animationSpec = FloatExponentialDecaySpec(),
            initialValue = 0f,
            initialVelocity = screenSize * 1.5f
        )
    }
    val transition = remember { mutableFloatStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .clipToBounds()
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(y = transition.floatValue.dp)
                .background(Color.Red)
                .align(Alignment.TopCenter)
        )
    }

    LaunchedEffect(key1 = animation) {
        val startTime = withFrameNanos { it }
        var frameCounter = 0
        do {
            val playTime = withFrameNanos { it } - startTime
            if (frameCounter % 3 == 1) {
                transition.floatValue = animation.getValueFromNanos(playTime)
                Log.d("DecayAnimationScreen", "state: ${transition.floatValue}")
            }
            frameCounter++
        } while (isActive)
    }
}