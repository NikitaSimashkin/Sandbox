package ru.kram.userprovider.presentation.di

import org.koin.dsl.module
import ru.kram.userprovider.presentation.mapper.UserToUserUiMapper
import ru.kram.userprovider.presentation.mapper.UserUiToUserMapper

val uiMapperModule = module {
	single { UserUiToUserMapper() }
	single { UserToUserUiMapper() }
}