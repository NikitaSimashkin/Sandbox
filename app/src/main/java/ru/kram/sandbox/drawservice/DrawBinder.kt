package ru.kram.sandbox.drawservice

import android.os.Binder
import android.util.Log
import java.lang.ref.WeakReference

class DrawBinder(drawer: Drawer) : Binder() {
	private val drawerRef = WeakReference(drawer)

	fun drawRedCircle() {
		Log.d(TAG, "drawRedCircle")
		drawerRef.get()?.drawRedCircle()
	}

	fun drawBigRedCircle() {
		Log.d(TAG, "drawBigRedCircle")
		drawerRef.get()?.drawBigRedCircle()
	}

	fun hideRedCircle() {
		Log.d(TAG, "hideRedCircle")
		drawerRef.get()?.hideRedCircle()
	}

	interface Drawer {
		fun drawRedCircle()
		fun drawBigRedCircle()
		fun hideRedCircle()
	}

	companion object {
		private const val TAG = "DrawBinder"
	}
}