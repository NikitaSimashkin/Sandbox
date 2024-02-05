package ru.kram.sandbox.experiments

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.EmptyCoroutineContext

class CoroutineLatch(count: Int) {

	@Volatile
	private var currentCount = count
	@Volatile
	private var isActive = count > 0

	private val coroutineScope = CoroutineScope(EmptyCoroutineContext)
	private val mutex = Mutex()
	private val completableDeferred = CompletableDeferred<Unit>()

	fun countDown() {
		coroutineScope.launch {
			mutex.withLock {
				if (!this@CoroutineLatch.isActive) return@launch

				currentCount--
				if (currentCount == 0) {
					completableDeferred.complete(Unit)
					this@CoroutineLatch.isActive = false
					coroutineScope.coroutineContext.cancelChildren()
				}
			}
		}
	}

	suspend fun await() {
		val localIsActive = mutex.withLock { isActive }
		if (!localIsActive) return

		try {
			completableDeferred.await()
		} catch (ignored: Exception) {}
	}
}
