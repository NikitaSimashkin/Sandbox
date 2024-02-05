package ru.kram.provider.presentation.di

import org.koin.dsl.module
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper
import ru.kram.provider.presentation.updateuser.UpdateUserViewModelFactory
import ru.kram.provider.presentation.userlist.UserListFragment
import ru.kram.provider.presentation.userlist.UserlistViewModelFactory

val userlistFragmentModule = module {
	scope<UserListFragment> {

	}
	single { UserlistViewModelFactory(get(), get()) }
}