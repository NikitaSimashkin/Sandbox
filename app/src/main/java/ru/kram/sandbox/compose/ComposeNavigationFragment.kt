package ru.kram.sandbox.compose

import AnimatedVector2DScreen
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kram.sandbox.compose.animation.AnimatableAsStateScreen
import ru.kram.sandbox.compose.animation.AnimatableScreen
import ru.kram.sandbox.compose.animation.AnimatedContentScreen
import ru.kram.sandbox.compose.animation.AnimatedVisibilityScreen
import ru.kram.sandbox.compose.animation.CrossFadeScreen
import ru.kram.sandbox.compose.animation.DecayAnimationScreen
import ru.kram.sandbox.compose.animation.TargetBasedAnimationScreen
import ru.kram.sandbox.compose.animation.UpdateTransitionScreen
import ru.kram.sandbox.compose.dispose.DisposeScreen
import ru.kram.sandbox.compose.edgetoedge.EdgeToEdgeActivity
import ru.kram.sandbox.utils.ComposeFragment

class ComposeNavigationFragment: ComposeFragment() {

    private val state = MutableStateFlow<ComposeScreenState>(ComposeScreenState.NavigationScreen)

    @Composable
    override fun Content() {
        val currentState by state.collectAsStateWithLifecycle()
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                when (currentState) {
                    ComposeScreenState.NavigationScreen -> Navigation()
                    ComposeScreenState.Animatable -> AnimatableScreen()
                    ComposeScreenState.AnimatedVisibility -> AnimatedVisibilityScreen()
                    ComposeScreenState.AnimateAsState -> AnimatableAsStateScreen()
                    ComposeScreenState.UpdateTransition -> UpdateTransitionScreen()
                    ComposeScreenState.TargetBasedAnimation -> TargetBasedAnimationScreen()
                    ComposeScreenState.DecayAnimation -> DecayAnimationScreen()
                    ComposeScreenState.AnimatedVector2D -> AnimatedVector2DScreen()
                    ComposeScreenState.AnimatedContent -> AnimatedContentScreen()
                    ComposeScreenState.CrossFade -> CrossFadeScreen()
                    ComposeScreenState.LaunchDispose -> DisposeScreen()
                }
            }

            if (currentState != ComposeScreenState.NavigationScreen) {
                Button(
                    onClick = {
                        state.value = ComposeScreenState.NavigationScreen
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Go Back")
                }
            }
        }
    }

    @Composable
    fun Navigation() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf(
                ComposeScreenState.AnimatedVisibility to "Animated Visibility",
                ComposeScreenState.Animatable to "Animatable",
                ComposeScreenState.AnimateAsState to "Animate As State",
                ComposeScreenState.UpdateTransition to "Update Transition",
                ComposeScreenState.TargetBasedAnimation to "Base Animation",
                ComposeScreenState.DecayAnimation to "Decay Animation",
                ComposeScreenState.AnimatedVector2D to "Animate Vector2D",
                ComposeScreenState.AnimatedContent to "Animated Content",
                ComposeScreenState.CrossFade to "CrossFade",
                ComposeScreenState.LaunchDispose to "Launch / Dispose",
            ).forEach { (state, name) ->
                item {
                    Button(
                        onClick = { this@ComposeNavigationFragment.state.value = state }
                    ) {
                        Text(name)
                    }
                }
            }
        }
    }
}