package ru.kram.deathstar.aidlservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import ru.kram.sandbox.common.utils.ProcessNameProvider
import ru.kram.sandbox.common.contract_deathstar.aidl.DeathStar
import ru.kram.sandbox.common.contract_deathstar.aidl.IDeathStarResponseListener
import ru.kram.sandbox.common.contract_deathstar.aidl.IDeathStarService
import timber.log.Timber

class DeathStarService: Service() {

	override fun onBind(intent: Intent?): IBinder {
		return binder
	}

	private val binder = object: IDeathStarService.Stub() {
		override fun fillDeathStarAsync(listener: IDeathStarResponseListener) {
			Timber.d("fillDeathStarAsync, ${ProcessNameProvider.getThreadAndProcessInfo()}")
			val star = DeathStar().apply {
				width = 1
				height = 2
				weight = 3
				owner = "fillDeathStarAsync"
			}
			listener.onResponse(star)
		}

		override fun fillDeathStarIn(deathStar: DeathStar) {
			Timber.d("fillDeathStarIn, receive $deathStar ${ProcessNameProvider.getThreadAndProcessInfo()}")
			deathStar.apply {
				width = 1
				height = 2
				weight = 3
				owner = "fillDeathStarIn"
			}
		}

		override fun fillDeathStarSync(deathStar: DeathStar) {
			Timber.d("fillDeathStarSync, receive $deathStar ${ProcessNameProvider.getThreadAndProcessInfo()}")
			deathStar.apply {
				width = 5
				owner = "fillDeathStarSync"
			}
		}

		override fun inoutFillDeathStar(deathStar: DeathStar) {
			Timber.d("inoutFillDeathStar, receive $deathStar ${ProcessNameProvider.getThreadAndProcessInfo()}")
			deathStar.height = 10
			deathStar.owner = "inoutFillDeathStar"
		}
	}

	companion object {
		private const val TAG = "DeathStarService"
	}
}