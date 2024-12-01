package ru.kram.sandbox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.lang.Exception


class FlowCollectTest {

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
				println("$it")
			}
			println("log after collect")
		}

		flowJob.join()
	}

	@Test
	fun a() {

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

	private fun asLiveData() = runBlocking {
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
		intFlow.asLiveData()
	}

	private fun <T> Flow<T>.asMyLiveData(): LiveData<T> {
		val liveData = MutableLiveData<T>()
		val job = coroutineScope.launch {
			collect {
				liveData.postValue(it)
			}
		}
		return liveData
	}

	@AfterEach
	fun tearDown() {
		coroutineScope.cancel()
	}
}