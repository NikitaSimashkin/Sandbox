package ru.kram.sandbox.features.pendingintent.di

import org.koin.dsl.module
import ru.kram.sandbox.features.pendingintent.ActivityComponentNameProvider

fun getPendingIntentModule(activityComponentNameProvider: ActivityComponentNameProvider) = module {
    single { activityComponentNameProvider }
}