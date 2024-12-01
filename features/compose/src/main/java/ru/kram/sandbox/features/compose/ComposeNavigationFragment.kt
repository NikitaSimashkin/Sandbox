package ru.kram.sandbox.features.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kram.sandbox.common.compose.ComposeFragment
import ru.kram.sandbox.features.compose.animation.AnimatableAsStateScreen
import ru.kram.sandbox.features.compose.animation.AnimatableScreen
import ru.kram.sandbox.features.compose.animation.AnimatedContentScreen
import ru.kram.sandbox.features.compose.animation.AnimatedVisibilityScreen
import ru.kram.sandbox.features.compose.animation.CrossFadeScreen
import ru.kram.sandbox.features.compose.animation.DecayAnimationScreen
import ru.kram.sandbox.features.compose.animation.TargetBasedAnimationScreen
import ru.kram.sandbox.features.compose.animation.UpdateTransitionScreen
import ru.kram.sandbox.features.compose.effect.DerivedStateScreen
import ru.kram.sandbox.features.compose.effect.DisposeScreen
import ru.kram.sandbox.features.compose.effect.SideEffectScreen
import ru.kram.sandbox.features.compose.effect.SnapshotFlowScreen
import ru.kram.sandbox.features.compose.grid.LazyGridScreen
import ru.kram.sandbox.features.compose.remember.RememberScreen

class ComposeNavigationFragment: ComposeFragment() {
    @Composable
    override fun Content() {
        val vm: ComposeNavigationViewModel = viewModel()
        val state = vm.state.collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                when (state.value) {
                    ComposeScreenState.NavigationScreen -> Navigation(vm::navigateTo)
                    ComposeScreenState.Animatable -> AnimatableScreen()
                    ComposeScreenState.AnimatedVisibility -> AnimatedVisibilityScreen()
                    ComposeScreenState.AnimateAsState -> AnimatableAsStateScreen()
                    ComposeScreenState.UpdateTransition -> UpdateTransitionScreen()
                    ComposeScreenState.TargetBasedAnimation -> TargetBasedAnimationScreen()
                    ComposeScreenState.DecayAnimation -> DecayAnimationScreen()
                    ComposeScreenState.AnimatedVector2D -> {}//AnimatedVector2DScreen()
                    ComposeScreenState.AnimatedContent -> AnimatedContentScreen()
                    ComposeScreenState.CrossFade -> CrossFadeScreen()
                    ComposeScreenState.LaunchDispose -> DisposeScreen()
                    ComposeScreenState.Remember -> RememberScreen()
                    ComposeScreenState.SideEffect -> SideEffectScreen()
                    ComposeScreenState.DerivedState -> DerivedStateScreen()
                    ComposeScreenState.SnapshotFlow -> SnapshotFlowScreen()
                    ComposeScreenState.LazyGrid ->  LazyGridScreen()
                }
            }

            if (state.value != ComposeScreenState.NavigationScreen) {
                Button(
                    onClick = {
                        vm.navigateTo(ComposeScreenState.NavigationScreen)
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
    private fun Navigation(
        onScreenClick: (ComposeScreenState) -> Unit
    ) {
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
                ComposeScreenState.Remember to "Remember",
                ComposeScreenState.SideEffect to "Side Effect",
                ComposeScreenState.DerivedState to "Derived State",
                ComposeScreenState.SnapshotFlow to "Snapshot Flow",
                ComposeScreenState.LazyGrid to "Lazy Grid"
            ).forEach { (composeScreenState, name) ->
                item {
                    Button(
                        onClick = { onScreenClick(composeScreenState) }
                    ) {
                        Text(name)
                    }
                }
            }
        }
    }
}