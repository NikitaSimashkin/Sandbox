package ru.kram.provider.presentation.di

import org.koin.dsl.module
import ru.kram.provider.presentation.adduser.AddUserFragment
import ru.kram.provider.presentation.adduser.AddUserViewModelFactory
import ru.kram.provider.presentation.mapper.UserUiToUserMapper

val addFragmentModule = module {
	scope<AddUserFragment> {

	}
	single { AddUserViewModelFactory(get(), get()) }
}
