package ru.kram.sandbox.features.drawservice

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.WindowManager
import ru.kram.sandbox.features.drawservice.view.RedCircleView
import timber.log.Timber

class DrawService : Service(), DrawBinder.Drawer {

	private val windowManager by lazy { applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager }

	private var activeView: View? = null

	override fun onBind(intent: Intent?): IBinder {
		Timber.d("onBind")
		return DrawBinder(this)
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Timber.d("onStartCommand")
		return super.onStartCommand(intent, flags, startId)
	}

	override fun onDestroy() {
		super.onDestroy()
		Timber.d("onDestroy")
	}

	override fun drawRedCircle() {
		Timber.d("drawRedCircle")
		drawCircle((100..200).random())
	}

	override fun drawBigRedCircle() {
		Timber.d("drawBigRedCircle")
		drawCircle(WindowManager.LayoutParams.MATCH_PARENT)
	}

	private fun drawCircle(size: Int) {
		val view = RedCircleView(this.applicationContext)

		val xPos: Int
		val yPos: Int
		if (size == WindowManager.LayoutParams.MATCH_PARENT) {
			xPos = 0
			yPos = 0
		} else {
			val screenWidth = resources.displayMetrics.widthPixels
			val screenHeight = resources.displayMetrics.heightPixels

			xPos = ((-screenWidth/2 + size)..(screenWidth/2 - size)).random()
			yPos = ((-screenHeight/2 + size)..(screenHeight/2 - size)).random()
		}
		val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
		} else {
			WindowManager.LayoutParams.TYPE_PHONE
		}
		val params = WindowManager.LayoutParams(
			size,
			size,
			xPos,
			yPos,
			type,
			WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
			PixelFormat.TRANSLUCENT
		)

		activeView = view
		windowManager.addView(view, params)
	}

	override fun hideRedCircle() {
		windowManager.removeView(activeView)
	}
}