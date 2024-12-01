package ru.kram.sandbox.head

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.work.Configuration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kram.sandbox.head.di.appModule
import ru.kram.sandbox.head.notification.NotificationChannelStorage
import timber.log.Timber

class HeadApp: Application(), Configuration.Provider {

	private val notificationChannelStorage = NotificationChannelStorage()

	override val workManagerConfiguration: Configuration
		get() = Configuration.Builder()
			.setExecutor(Dispatchers.IO.asExecutor())
			.setMinimumLoggingLevel(Log.DEBUG)
			.build()

	override fun onCreate() {
        Timber.tag(TAG).d("onCreate")
		super.onCreate()

		createNotificationChannels()

		startKoin {
			androidContext(this@HeadApp)
			modules(appModule)
		}

		Timber.plant(Timber.DebugTree())
		View(this).measuredWidth
	}

	private fun createNotificationChannels() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			notificationChannelStorage.getNotificationChannels().forEach {
				getSystemService(NotificationManager::class.java).createNotificationChannel(it)
			}
		}
	}

	companion object {
		private const val TAG = "SandboxApp"
	}
}