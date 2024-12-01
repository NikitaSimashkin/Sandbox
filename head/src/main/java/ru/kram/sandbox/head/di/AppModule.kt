package ru.kram.sandbox.head.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.kram.sandbox.features.biglist.domain.FragmentOpener
import ru.kram.sandbox.features.pendingintent.ActivityComponentNameProvider
import ru.kram.sandbox.features.service.ActivityIntentProvider
import ru.kram.sandbox.head.MainActivity
import ru.kram.sandbox.head.dependencies.FragmentOpenerImpl
import ru.kram.sandbox.head.dependencies.ActivityComponentNameProviderImpl
import ru.kram.sandbox.head.dependencies.ActivityIntentProviderImpl

val appModule = module {

    scope<MainActivity> {
        scoped<FragmentOpener> { FragmentOpenerImpl() }
        scoped<ActivityComponentNameProvider> { ActivityComponentNameProviderImpl(androidApplication()) }
        scoped<ActivityIntentProvider> { ActivityIntentProviderImpl(androidApplication()) }
    }
}