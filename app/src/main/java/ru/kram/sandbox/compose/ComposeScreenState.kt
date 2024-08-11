package ru.kram.sandbox.compose

sealed class ComposeScreenState {
    data object NavigationScreen: ComposeScreenState()
    data object AnimatedVisibility: ComposeScreenState()
    data object Animatable : ComposeScreenState()
    data object AnimateAsState : ComposeScreenState()
    data object UpdateTransition: ComposeScreenState()
    data object TargetBasedAnimation: ComposeScreenState()
    data object DecayAnimation: ComposeScreenState()
    data object AnimatedVector2D: ComposeScreenState()
    data object AnimatedContent: ComposeScreenState()
    data object CrossFade: ComposeScreenState()
    data object LaunchDispose: ComposeScreenState()
    data object Remember: ComposeScreenState()
    data object SideEffect: ComposeScreenState()
    data object DerivedState: ComposeScreenState()
    data object SnapshotFlow: ComposeScreenState()
}