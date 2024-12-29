package ru.kram.sandbox.features.tvcompose.details.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.kram.sandbox.features.tvcompose.details.domain.GetCardDetailsUseCase
import ru.kram.sandbox.features.tvcompose.details.presentation.model.CardDetailsData

internal class CardDetailsViewModel(
    private val getCardDetailsUseCase: GetCardDetailsUseCase,
): ViewModel() {

    val state = MutableStateFlow<CardDetailsData?>(null)

    fun loadCardDetails(contentId: String) {
        state.value = getCardDetailsUseCase.getCardDetails(contentId)
    }
}