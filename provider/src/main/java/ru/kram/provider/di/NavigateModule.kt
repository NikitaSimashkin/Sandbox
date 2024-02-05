package ru.kram.provider.di

import org.koin.dsl.module
import ru.kram.provider.presentation.ProviderActivity
import ru.kram.provider.presentation.navigation.DatabaseRouter
import ru.kram.provider.presentation.navigation.DatabaseRouterImpl

val navigateModule = module {
	scope<ProviderActivity> {
		scoped<DatabaseRouter> { DatabaseRouterImpl(get()) }
	}
}