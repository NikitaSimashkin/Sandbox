package ru.kram.provider.di

import android.content.ContentResolver
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.koin.dsl.onClose
import ru.kram.provider.data.repository.UserRepositoryImpl
import ru.kram.provider.domain.UserRepository
import ru.kram.provider_contract.UserProviderApi
import ru.kram.provider_contract.UserProviderApiImpl

val repositoryModule = module {
	single<ContentResolver> { androidApplication().contentResolver }
	single<UserProviderApi> { UserProviderApi.create(get()) }
	single<UserRepository> { UserRepositoryImpl(get(), Dispatchers.IO) }
}