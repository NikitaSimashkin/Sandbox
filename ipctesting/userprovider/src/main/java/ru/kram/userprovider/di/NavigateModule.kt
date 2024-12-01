package ru.kram.userprovider.di

import org.koin.dsl.module
import ru.kram.userprovider.presentation.ProviderActivity
import ru.kram.userprovider.presentation.navigation.DatabaseRouter
import ru.kram.userprovider.presentation.navigation.DatabaseRouterImpl

val navigateModule = module {
	scope<ProviderActivity> {
		scoped<DatabaseRouter> { DatabaseRouterImpl(get()) }
	}
}