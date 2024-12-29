package ru.kram.sandbox.features.tvcompose.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kram.sandbox.features.tvcompose.details.domain.GetCardDetailsUseCase
import ru.kram.sandbox.features.tvcompose.details.presentation.CardDetailsViewModel

val cardDetailsModule = module {
    scope<CardDetailsScope> {
        scoped { GetCardDetailsUseCase() }

        viewModel { CardDetailsViewModel(get()) }
    }
}