package ru.kram.sandbox.features.drawservice

import android.os.Binder
import android.util.Log
import timber.log.Timber
import java.lang.ref.WeakReference

internal class DrawBinder(drawer: Drawer) : Binder() {
	private val drawerRef = WeakReference(drawer)

	fun drawRedCircle() {
		Timber.d("drawRedCircle")
		drawerRef.get()?.drawRedCircle()
	}

	fun drawBigRedCircle() {
		Timber.d("drawBigRedCircle")
		drawerRef.get()?.drawBigRedCircle()
	}

	fun hideRedCircle() {
		Timber.d("hideRedCircle")
		drawerRef.get()?.hideRedCircle()
	}

	interface Drawer {
		fun drawRedCircle()
		fun drawBigRedCircle()
		fun hideRedCircle()
	}
}