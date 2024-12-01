package ru.kram.userprovider.presentation.di

import org.koin.dsl.module
import ru.kram.userprovider.presentation.updateuser.UpdateUserFragment
import ru.kram.userprovider.presentation.updateuser.UpdateUserViewModelFactory

val updateUserFragmentModule = module {
	scope<UpdateUserFragment> {

	}
	single { UpdateUserViewModelFactory(get(), get(), get()) }
}