package ru.kram.sandbox.features.biglist.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kram.sandbox.features.biglist.data.UserRepositoryImpl
import ru.kram.sandbox.features.biglist.domain.FragmentOpener
import ru.kram.sandbox.features.biglist.domain.UserRepository
import ru.kram.sandbox.features.biglist.presentation.mapper.UserToUserUiMapper
import ru.kram.sandbox.features.biglist.presentation.stateholder.UserViewModel

fun getBigListModule(
    fragmentOpener: FragmentOpener
) = module {

    single {
        UserToUserUiMapper()
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    viewModel {
        UserViewModel(
            repository = get(),
            mapper = get(),
            fragmentOpener = fragmentOpener
        )
    }
}