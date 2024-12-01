package ru.kram.sandbox.features.compose.edgetoedge

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
internal class EdgeToEdgeViewModel: ViewModel() {

    val state = MutableStateFlow(getInitialState())
    val command = MutableSharedFlow<Command>()

    fun navigateToBottomSheetScreen() {
        state.update {
            it.copy(screen = EdgeToEdgeScreen.BottomSheetScreen2)
        }
    }

    fun enableEdgeToEdge() {
        viewModelScope.launch {
            command.emit(Command.EnableEdgeToEdge)
        }
    }

    fun disableEdgeToEdge() {
        viewModelScope.launch {
            command.emit(Command.DisableEdgeToEdge)
        }
    }

    fun navigateToNavigationScreen() {
        state.update {
            it.copy(screen = EdgeToEdgeScreen.NavigationScreen)
        }
    }

    fun enableStatusBarPadding() {
        state.update {
            it.copy(isStatusBarPaddingEnabled = true)
        }
    }

    fun disableStatusBarPadding() {
        state.update {
            it.copy(isStatusBarPaddingEnabled = false)
        }
    }

    fun enableNavigationBarPadding() {
        state.update {
            it.copy(isNavigationBarPaddingEnabled = true)
        }
    }

    fun disableNavigationBarPadding() {
        state.update {
            it.copy(isNavigationBarPaddingEnabled = false)
        }
    }

    fun changeStatusBarPaddingOnButton() {
        state.update {
            it.copy(isStatusBarPaddingOnButtonEnabled = !it.isStatusBarPaddingOnButtonEnabled)
        }
    }

    fun changeNavigationBarPaddingOnButton() {
        state.update {
            it.copy(isNavigationBarPaddingOnButtonEnabled = !it.isNavigationBarPaddingOnButtonEnabled)
        }
    }

    fun enableSafeDrawingPadding() {
        state.update {
            it.copy(isSafeDrawingPaddingEnabled = true)
        }
    }

    fun disableSafeDrawingPadding() {
        state.update {
            it.copy(isSafeDrawingPaddingEnabled = false)
        }
    }

    fun enableSystemBarsPadding() {
        state.update {
            it.copy(isSystemBarsPaddingEnabled = true)
        }
    }

    fun disableSystemBarsPadding() {
        state.update {
            it.copy(isSystemBarsPaddingEnabled = false)
        }
    }

    fun enableDisplayCutoutPadding() {
        state.update {
            it.copy(isDisplayCutoutPaddingEnabled = true)
        }
    }

    fun disableDisplayCutoutPadding() {
        state.update {
            it.copy(isDisplayCutoutPaddingEnabled = false)
        }
    }

    fun enableImePadding() {
        state.update {
            it.copy(isImePaddingEnabled = true)
        }
    }

    fun disableImePadding() {
        state.update {
            it.copy(isImePaddingEnabled = false)
        }
    }

    fun consumeStatusBarPadding() {
        state.update {
            it.copy(isConsumedStatusBarPadding = true)
        }
    }

    fun unconsumeStatusBarPadding() {
        state.update {
            it.copy(isConsumedStatusBarPadding = false)
        }
    }

    fun hideNavBar() {
        viewModelScope.launch {
            command.emit(Command.HideNavBar)
        }
    }

    fun showNavBar() {
        viewModelScope.launch {
            command.emit(Command.ShowNavBar)
        }
    }

    fun enableNavBarPaddingIgnoreVisibility() {
        state.update {
            it.copy(isNavBarPaddingIgnoreVisibilityEnabled = true)
        }
    }

    fun disableNavBarPaddingIgnoreVisibility() {
        state.update {
            it.copy(isNavBarPaddingIgnoreVisibilityEnabled = false)
        }
    }

    fun clearState() {
        state.update {
            getInitialState()
        }
        viewModelScope.launch {
            command.emit(Command.DisableEdgeToEdge)
        }
    }

    private fun getInitialState() = EdgeToEdgeState(
        isStatusBarPaddingEnabled = false,
        isNavigationBarPaddingEnabled = false,
        isStatusBarPaddingOnButtonEnabled = false,
        isNavigationBarPaddingOnButtonEnabled = false,
        isSafeDrawingPaddingEnabled = false,
        isSystemBarsPaddingEnabled = false,
        isDisplayCutoutPaddingEnabled = false,
        isImePaddingEnabled = false,
        isConsumedStatusBarPadding = false,
        isNavBarPaddingIgnoreVisibilityEnabled = false,

    )

    sealed interface Command {
        data object EnableEdgeToEdge: Command
        data object DisableEdgeToEdge: Command
        data object HideNavBar: Command
        data object ShowNavBar: Command
    }
}