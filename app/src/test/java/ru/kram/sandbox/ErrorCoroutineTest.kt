package ru.kram.sandbox

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test

class ErrorCoroutineTest {

	private val context = StandardTestDispatcher() + CoroutineName("ErrorCoroutineTest")
	private val handler = CoroutineExceptionHandler { _, throwable ->
		println("CoroutineExceptionHandler")
	}

	@Before
	fun setUp() {
		Dispatchers.setMain(StandardTestDispatcher())
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	@Test
	fun testLaunchException() = runTest {
		try {
			launch {
				throw NullPointerException()
			}.join()
		} catch (ignored: Exception) {
			println("catch testLaunchException")
		}
	}

	@Test
	fun testLaunchException2() = runTest(context) {
		launch {
			try {
				throw NullPointerException()
			} catch (ignored: Exception) {
				println("catch testLaunchException2")
			}
		}.join()
	}

	@Test
	fun testLaunchException3() = runTest(context) {
		launch {
			try {
				launch {
					throw NullPointerException()
				}.join()
			} catch (ignored: Exception) {
				println("catch testLaunchException3")
			}
		}.join()
	}

	@Test
	fun testAsyncException() = runTest(context) {
		try {
			async {
				throw NullPointerException()
			}.join()
		} catch (ignored: Exception) {
			println("catch testAsyncException")
		}
	}

	@Test
	fun testAsyncException2() = runTest(context) {
		async {
			try {
				throw NullPointerException()
			} catch (ignored: Exception) {
				println("catch testAsyncException2")
			}
		}.join()
	}

	@Test
	fun testAsyncException3() = runTest(context) {
		async {
			try {
				async {
					throw NullPointerException()
				}.join()
			} catch (ignored: Exception) {
				println("catch testAsyncException3")
			}
		}.join()

		println("after testAsyncException3")
	}

	@Test
	fun testLaunchException4() = runTest(context) {
		launch(handler) {
			throw NullPointerException()
		}.join()
	}

	@Test
	fun testAsyncException4() = runTest(context) {
		val handler = CoroutineExceptionHandler { _, throwable ->
			println("CoroutineExceptionHandler")
		}
		async(handler) {
			throw NullPointerException()
		}.join()
	}
}