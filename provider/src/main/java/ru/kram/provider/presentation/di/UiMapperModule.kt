package ru.kram.provider.presentation.di

import org.koin.dsl.module
import ru.kram.provider.presentation.mapper.UserToUserUiMapper
import ru.kram.provider.presentation.mapper.UserUiToUserMapper

val uiMapperModule = module {
	single { UserUiToUserMapper() }
	single { UserToUserUiMapper() }
}