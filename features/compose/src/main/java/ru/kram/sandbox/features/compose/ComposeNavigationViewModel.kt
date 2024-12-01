package ru.kram.sandbox.features.compose

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class ComposeNavigationViewModel: ViewModel() {

    val state = MutableStateFlow<ComposeScreenState>(ComposeScreenState.NavigationScreen)

    fun navigateTo(screen: ComposeScreenState) {
        state.value = screen
    }
}