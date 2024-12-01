package ru.kram.sandbox.features.compose.edgetoedge

internal sealed class EdgeToEdgeScreen {
    data object NavigationScreen: EdgeToEdgeScreen()
    data object BottomSheetScreen2: EdgeToEdgeScreen()
}

internal data class EdgeToEdgeState(
    val screen: EdgeToEdgeScreen = EdgeToEdgeScreen.NavigationScreen,

    val isStatusBarPaddingEnabled: Boolean,
    val isNavigationBarPaddingEnabled: Boolean,

    val isStatusBarPaddingOnButtonEnabled: Boolean,
    val isNavigationBarPaddingOnButtonEnabled: Boolean,

    val isSafeDrawingPaddingEnabled: Boolean,
    val isSystemBarsPaddingEnabled: Boolean,
    val isDisplayCutoutPaddingEnabled: Boolean,
    val isImePaddingEnabled: Boolean,

    val isConsumedStatusBarPadding: Boolean,

    val isNavBarPaddingIgnoreVisibilityEnabled: Boolean,
)