package ru.kram.userprovider.presentation.di

import org.koin.dsl.module
import ru.kram.userprovider.presentation.userlist.UserListFragment
import ru.kram.userprovider.presentation.userlist.UserlistViewModelFactory

val userlistFragmentModule = module {
	scope<UserListFragment> {

	}
	single { UserlistViewModelFactory(get(), get()) }
}