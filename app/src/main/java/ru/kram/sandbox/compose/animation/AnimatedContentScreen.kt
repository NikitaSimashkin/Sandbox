package ru.kram.sandbox.compose.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        var isPlusEnabled by remember { mutableStateOf(false) }
        var state by remember { mutableIntStateOf(15) }
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                if (!isPlusEnabled) {
                    slideInVertically(animationSpec = tween(900, easing = LinearEasing)) {
                        height -> height
                    } + fadeIn(animationSpec = tween(900, easing = LinearEasing)) togetherWith slideOutVertically(animationSpec = tween(900, easing = LinearEasing)) {
                        height -> -height
                    } + fadeOut(animationSpec = tween(900, easing = LinearEasing)) using SizeTransform { initialSize, targetSize ->
                        spring()
                    }
                } else {
                    slideInVertically(animationSpec = tween(900, easing = LinearEasing)) {
                        height -> -height
                    } + fadeIn(animationSpec = tween(900, easing = LinearEasing)) togetherWith slideOutVertically(animationSpec = tween(900, easing = LinearEasing)) {
                        height -> height
                    } + fadeOut(animationSpec = tween(900, easing = LinearEasing))
                }
            },
            modifier = Modifier
                .align(Alignment.Center),
            label = ""
        ) { targetState ->
            Box(modifier = Modifier
                .size(100.dp)
                .clickable {
                    isPlusEnabled = !isPlusEnabled
                }
            ) {
                Text(
                    text = targetState.toString(),
                    fontSize = 40.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                       // .animateContentSize()
                       // .animateEnterExit()
                )

            }
        }

        LaunchedEffect(key1 = Unit) {
            launch {
                isPlusEnabled = true
                while(true) {
                    delay(1000)
                    state += if (isPlusEnabled) 1 else -1
                }
            }
        }
    }
}