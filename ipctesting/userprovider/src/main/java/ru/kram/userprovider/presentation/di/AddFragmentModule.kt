package ru.kram.userprovider.presentation.di

import org.koin.dsl.module
import ru.kram.userprovider.presentation.adduser.AddUserFragment
import ru.kram.userprovider.presentation.adduser.AddUserViewModelFactory

val addFragmentModule = module {
	scope<AddUserFragment> {

	}
	single { AddUserViewModelFactory(get(), get()) }
}
