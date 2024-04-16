package ru.kram.sandbox.compose.animation.model

sealed class AnimationScreenState {
    data object NavigationScreen: AnimationScreenState()
    data object AnimatedVisibility: AnimationScreenState()
    data object Animatable : AnimationScreenState()
    data object AnimateAsState : AnimationScreenState()
}