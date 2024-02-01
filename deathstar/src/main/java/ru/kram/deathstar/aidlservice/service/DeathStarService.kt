package ru.kram.deathstar.aidlservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import ru.kram.common.util.process.ProcessNameProvider
import ru.kram.deathstar.deathstar_contract.DeathStar
import ru.kram.deathstar.deathstar_contract.aidl.IDeathStarResponseListener
import ru.kram.deathstar.deathstar_contract.aidl.IDeathStarService

class DeathStarService: Service() {

	override fun onBind(intent: Intent?): IBinder {
		return binder
	}

	private val binder = object: IDeathStarService.Stub() {
		override fun fillDeathStarAsync(listener: IDeathStarResponseListener) {
			Log.d(TAG, "fillDeathStarAsync, ${ProcessNameProvider.getThreadAndProcessInfo()}")
			val star = DeathStar().apply {
				width = 1
				height = 2
				weight = 3
				owner = "fillDeathStarAsync"
			}
			listener.onResponse(star)
		}

		override fun fillDeathStarIn(deathStar: DeathStar) {
			Log.d(TAG, "fillDeathStarIn, receive $deathStar ${ProcessNameProvider.getThreadAndProcessInfo()}")
			deathStar.apply {
				width = 1
				height = 2
				weight = 3
				owner = "fillDeathStarIn"
			}
		}

		override fun fillDeathStarSync(deathStar: DeathStar) {
			Log.d(TAG, "fillDeathStarSync, receive $deathStar ${ProcessNameProvider.getThreadAndProcessInfo()}")
			deathStar.apply {
				width = 5
				owner = "fillDeathStarSync"
			}
		}

		override fun inoutFillDeathStar(deathStar: DeathStar) {
			Log.d(TAG, "inoutFillDeathStar, receive $deathStar ${ProcessNameProvider.getThreadAndProcessInfo()}")
			deathStar.height = 10
			deathStar.owner = "inoutFillDeathStar"
		}
	}

	companion object {
		private const val TAG = "DeathStarService"
	}
}