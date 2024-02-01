package ru.kram.sandbox

import android.util.Log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import java.lang.Exception
import java.util.Stack
import kotlin.math.abs

class FlowTest {

	private val coroutineScope = CoroutineScope(Dispatchers.IO + CoroutineName("FlowTest"))
	@Test
	fun testCollect() = runBlocking {
		val intFlow = flow<Int> {
			emit(5)
			delay(1000)
			emit(10)
			delay(1000)
			emit(20)
			delay(1000)
			emit(30)
			delay(1000)
			emit(40)
			delay(1000)
			emit(50)
			delay(1000)
			emit(60)
		}

		val flowJob = coroutineScope.launch {
			intFlow.collect {
				println("FlowTest $it")
			}
			println("FlowTest log after collect")
		}

		flowJob.join()
	}

	@Test
	fun testLaunchException() = runBlocking {
		try {
			coroutineScope.launch {
				throw NullPointerException()
			}.join()
		} catch (ignored: Exception) {}

		val b = coroutineScope.launch {
			try {
				throw IllegalArgumentException()
			} catch (ignored: Exception) {}
		}.join()
	}

	@After
	fun tearDown() {
		coroutineScope.cancel()
	}
}