package ru.kram.sandbox.compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ComposeNavigationViewModel: ViewModel() {

    val state = MutableStateFlow<ComposeScreenState>(ComposeScreenState.NavigationScreen)

    fun navigateTo(screen: ComposeScreenState) {
        state.value = screen
    }
}