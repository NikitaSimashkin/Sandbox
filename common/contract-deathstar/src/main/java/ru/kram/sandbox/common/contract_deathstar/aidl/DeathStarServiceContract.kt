package ru.kram.sandbox.common.contract_deathstar.aidl

import android.content.ComponentName
import android.content.Intent

object DeathStarServiceContract {

	private const val DEATH_STAR_SERVICE_PACKAGE = "ru.kram.deathstar"
	private const val DEATH_STAR_SERVICE_CLASS = "ru.kram.deathstar.aidlservice.service.DeathStarService"

	fun getDeathStarServiceIntent(): Intent {
		return Intent().apply {
			`package` = DEATH_STAR_SERVICE_PACKAGE
			component = ComponentName(DEATH_STAR_SERVICE_PACKAGE, DEATH_STAR_SERVICE_CLASS)
		}
	}
}