package ru.kram.sandbox.features.service.di

import org.koin.dsl.module
import ru.kram.sandbox.features.service.ActivityIntentProvider

fun getServiceModule(
    startActivityIntentProvider: ActivityIntentProvider
) = module {
    single { startActivityIntentProvider }
}