package ru.kram.provider.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kram.provider.presentation.adduser.AddUserFragment
import ru.kram.provider.presentation.adduser.AddUserViewModelFactory
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper
import ru.kram.provider.presentation.updateuser.UpdateUserFragment
import ru.kram.provider.presentation.updateuser.UpdateUserViewModel
import ru.kram.provider.presentation.updateuser.UpdateUserViewModelFactory

val updateUserFragmentModule = module {
	scope<UpdateUserFragment> {

	}
	single { UpdateUserViewModelFactory(get(), get(), get()) }
}