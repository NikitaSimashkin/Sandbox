package ru.kram.userprovider.di

import android.content.ContentResolver
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.kram.userprovider.data.repository.UserRepositoryImpl
import ru.kram.userprovider.domain.UserRepository
import ru.kram.sandbox.common.contract_user_provider.UserProviderApi

val repositoryModule = module {
	single<ContentResolver> { androidApplication().contentResolver }
	single<UserProviderApi> { UserProviderApi.create(get()) }
	single<UserRepository> { UserRepositoryImpl(get(), Dispatchers.IO) }
}