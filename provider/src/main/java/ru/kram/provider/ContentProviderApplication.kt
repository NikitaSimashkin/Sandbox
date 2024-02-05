package ru.kram.provider

import android.app.Application
import android.content.Context
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.kram.provider.presentation.di.addFragmentModule
import ru.kram.provider.di.databaseModule
import ru.kram.provider.di.navigateModule
import ru.kram.provider.di.repositoryModule
import ru.kram.provider.presentation.di.uiMapperModule
import ru.kram.provider.presentation.di.updateUserFragmentModule
import ru.kram.provider.presentation.di.userlistFragmentModule

class ContentProviderApplication: Application() {

	override fun attachBaseContext(base: Context?) {
		super.attachBaseContext(base)
		startKoin {
			androidLogger(Level.DEBUG)
			androidContext(this@ContentProviderApplication)
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
		Log.d(TAG, "onCreate")
		super.onCreate()
	}

	companion object {
		private const val TAG = "ContentProviderApplication"
	}
}