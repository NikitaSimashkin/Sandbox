package ru.kram.sandbox.compose.edgetoedge

sealed class EdgeToEdgeScreen {
    data object NavigationScreen: EdgeToEdgeScreen()
}

data class EdgeToEdgeState(
    val screen: EdgeToEdgeScreen = EdgeToEdgeScreen.NavigationScreen,

    val isStatusBarPaddingEnabled: Boolean = false,
    val isNavigationBarPaddingEnabled: Boolean = false,

    val isStatusBarPaddingOnButtonEnabled: Boolean = false,
    val isNavigationBarPaddingOnButtonEnabled: Boolean = false,

    val isSafeDrawingPaddingEnabled: Boolean = false,
    val isSystemBarsPaddingEnabled: Boolean = false,
    val isDisplayCutoutPaddingEnabled: Boolean = false,
    val isImePaddingEnabled: Boolean = false,

    val isConsumedStatusBarPadding: Boolean = false,
)