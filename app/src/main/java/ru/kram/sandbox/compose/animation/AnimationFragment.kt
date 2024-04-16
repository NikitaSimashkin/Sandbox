package ru.kram.sandbox.compose.animation

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
import ru.kram.sandbox.compose.animation.model.AnimationScreenState
import ru.kram.sandbox.utils.ComposeFragment

class AnimationFragment: ComposeFragment() {

    private val state = MutableStateFlow<AnimationScreenState>(AnimationScreenState.NavigationScreen)

    @Composable
    override fun Content() {
        val currentState by state.collectAsStateWithLifecycle()
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                when (currentState) {
                    AnimationScreenState.NavigationScreen -> Navigation()
                    AnimationScreenState.Animatable -> AnimatableScreen()
                    AnimationScreenState.AnimatedVisibility -> AnimatedVisibilityScreen()
                    AnimationScreenState.AnimateAsState -> AnimatableAsStateScreen()
                }
            }

            if (currentState != AnimationScreenState.NavigationScreen) {
                Button(
                    onClick = {
                        state.value = AnimationScreenState.NavigationScreen
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

            item {
                Button(
                    onClick = { state.value = AnimationScreenState.AnimatedVisibility }
                ) {
                    Text("Animated Visibility")
                }
            }

            item {
                Button(
                    onClick = { state.value = AnimationScreenState.Animatable }
                ) {
                    Text("Animatable")
                }
            }

            item {
                Button(
                    onClick = { state.value = AnimationScreenState.AnimateAsState }
                ) {
                    Text("Animate As State")
                }
            }
        }
    }
}