package ru.kram.sandbox.head.navigation

import androidx.compose.runtime.Composable
import ru.kram.sandbox.common.compose.ComposeFragment
import ru.kram.sandbox.features.tvcompose.details.presentation.CardDetailsScreen

class CardDetailsScreenFragment: ComposeFragment() {

    @Composable
    override fun Content() {
        CardDetailsScreen("contentId")
    }
}