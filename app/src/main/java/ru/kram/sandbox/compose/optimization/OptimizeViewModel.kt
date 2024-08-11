package ru.kram.sandbox.compose.optimization

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class OptimizeViewModel: ViewModel() {

    val state = MutableStateFlow(OptimizeState())

    fun navigateTo(screen: OptimizeScreen) {
        state.update {
            it.copy(screen = screen)
        }
    }
}