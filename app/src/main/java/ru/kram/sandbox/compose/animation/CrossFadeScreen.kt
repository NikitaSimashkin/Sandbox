package ru.kram.sandbox.compose.animation

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CrossFadeScreen() {

    var isBlue by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { isBlue = !isBlue },
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isBlue,
            animationSpec = tween(durationMillis = 2000),
            label = ""
        ) { isBlueState ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(if (isBlueState) Color.Blue else Color.Red)
            ) {
                Text(
                    text = if (isBlueState) "Blue" else "Red",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 40.sp
                )
            }
        }
    }
}