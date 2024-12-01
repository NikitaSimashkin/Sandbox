package ru.kram.userprovider

import android.app.Application
import android.content.Context
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.kram.userprovider.presentation.di.addFragmentModule
import ru.kram.userprovider.di.databaseModule
import ru.kram.userprovider.di.navigateModule
import ru.kram.userprovider.di.repositoryModule
import ru.kram.userprovider.presentation.di.uiMapperModule
import ru.kram.userprovider.presentation.di.updateUserFragmentModule
import ru.kram.userprovider.presentation.di.userlistFragmentModule
import timber.log.Timber

class UserContentProviderApplication: Application() {

	override fun attachBaseContext(base: Context?) {
		super.attachBaseContext(base)
		startKoin {
			androidLogger(Level.DEBUG)
			androidContext(this@UserContentProviderApplication)
			modules(
				databaseModule,
				navigateModule,
				repositoryModule,
				addFragmentModule,
				userlistFragmentModule,
				updateUserFragmentModule,
				uiMapperModule
			)
		}
	}

	override fun onCreate() {
		Timber.d("onCreate")
		super.onCreate()
	}
}