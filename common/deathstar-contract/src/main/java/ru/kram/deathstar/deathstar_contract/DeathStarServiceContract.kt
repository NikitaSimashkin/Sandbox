package ru.kram.deathstar.deathstar_contract

import android.content.ComponentName
import android.content.Intent

object DeathStarServiceContract {

	private const val DEATH_STAR_SERVICE_PACKAGE = "ru.kram.deathstar"
	private const val DEATH_STAR_SERVICE_CLASS = "ru.kram.deathstar.aidlservice.service.DeathStarService"
	private const val DEATH_STAR_SERVICE_PERMISSION = "ru.kram.sandlib.permission.DEATH_STR_SERVICE_START"

	fun getDeathStarServiceIntent(): Intent {
		return Intent().apply {
			`package` = DEATH_STAR_SERVICE_PACKAGE
			component = ComponentName(DEATH_STAR_SERVICE_PACKAGE, DEATH_STAR_SERVICE_CLASS)
		}
	}
}