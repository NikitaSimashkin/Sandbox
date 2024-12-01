package ru.kram.sandbox.features.compose.optimization

internal sealed interface OptimizeScreen {
    data object NavigationScreen : OptimizeScreen
    data object NonRestartableScreen: OptimizeScreen
}

internal data class OptimizeState(
    val screen: OptimizeScreen = OptimizeScreen.NavigationScreen
)