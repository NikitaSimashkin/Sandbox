package ru.kram.sandbox.features.tvcompose.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.KoinApplication
import ru.kram.sandbox.features.tvcompose.details.di.CardDetailsScope
import ru.kram.sandbox.features.tvcompose.details.di.cardDetailsModule
import ru.kram.sandbox.features.tvcompose.details.presentation.composable.Title
import ru.kram.sandbox.features.tvcompose.details.presentation.composable.TrailerPlayer
import ru.kram.sandbox.features.tvcompose.details.presentation.model.TitleItem
import ru.kram.sandbox.features.tvcompose.details.presentation.theme.CardDetailsTheme
import ru.kram.sandbox.features.tvcompose.utils.rememberScope
import timber.log.Timber

@Composable
fun CardDetailsScreen(
    contentId: String,
    modifier: Modifier = Modifier
) {
    val scope = rememberScope<CardDetailsScope>()

    val viewModel: CardDetailsViewModel = koinViewModel(
        scope = scope
    )

    LaunchedEffect(Unit) {
        viewModel.loadCardDetails(contentId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateRef = state

    if (stateRef != null) {
        CardDetailsTheme {
            Box(modifier = modifier.fillMaxSize()) {
                TrailerPlayer(
                    videoItem = stateRef.trailer,
                )
                Title(
                    titleItem = stateRef.titleItem,
                    modifier = Modifier.padding(
                        vertical = 40.dp,
                        horizontal = 60.dp
                    ),
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            Timber.d("CardDetailsScreen disposed")
            scope.close()
        }
    }
}

private fun initDI(): KoinApplication {
    return KoinApplication
        .init()
        .modules(cardDetailsModule)
}