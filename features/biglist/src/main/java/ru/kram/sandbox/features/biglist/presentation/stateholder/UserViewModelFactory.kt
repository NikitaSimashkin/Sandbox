package ru.kram.sandbox.features.biglist.presentation.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kram.sandbox.features.biglist.domain.FragmentOpener
import ru.kram.sandbox.features.biglist.domain.UserRepository
import ru.kram.sandbox.features.biglist.presentation.mapper.UserToUserUiMapper

internal class UserViewModelFactory(
    private val repository: UserRepository,
    private val mapper: UserToUserUiMapper,
    private val fragmentOpener: FragmentOpener
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository, mapper, fragmentOpener) as T
    }
}