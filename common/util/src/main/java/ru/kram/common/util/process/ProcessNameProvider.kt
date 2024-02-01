package ru.kram.common.util.process

import android.annotation.SuppressLint
import android.os.Looper

@SuppressLint("PrivateApi")
object ProcessNameProvider {

	private const val TAG = "ProcessLogger"

	private val getProcessName = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentProcessName")

	fun getProcessName(): String {
		return getProcessName.invoke(null) as String
	}

	fun getThreadAndProcessInfo(): String {
		return """thread:${Thread.currentThread().name}, main:${Looper.getMainLooper() == Looper.myLooper()}, process:${getProcessName()}:${android.os.Process.myPid()}"""
	}
}