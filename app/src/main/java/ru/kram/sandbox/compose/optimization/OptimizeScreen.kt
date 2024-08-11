package ru.kram.sandbox.compose.optimization

sealed interface OptimizeScreen {
    data object NavigationScreen : OptimizeScreen
    data object NonRestartableScreen: OptimizeScreen
}

data class OptimizeState(
    val screen: OptimizeScreen = OptimizeScreen.NavigationScreen
)