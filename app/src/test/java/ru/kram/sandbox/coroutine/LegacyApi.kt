package ru.kram.sandbox.coroutine

import android.os.Handler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface Request

interface LegacyApi {
    fun doAsync(request: Request, callback: (Int) -> Unit)
}

val scope = CoroutineScope(EmptyCoroutineContext)

fun task(api: LegacyApi, requests: List<Request>, onSuccessful: () -> Unit) {
    val results = IntArray(requests.size)
    val lock = Any()

    requests.forEachIndexed { index, request ->
        api.doAsync(request) { result ->
            synchronized(lock) {
                results[index] = result

                if (results.all { it != 0 }) {
                    onSuccessful()
                }
            }
        }
    }
}

fun task2(api: LegacyApi, requests: List<Request>, onSuccessful: () -> Unit) {
    val scope = CoroutineScope(Dispatchers.Main)

    val list = requests.map { request ->
        scope.async(Dispatchers.IO) {
            suspendCoroutine { continuation ->
                api.doAsync(request) {
                    result -> continuation.resume(result)
                }
            }

            suspendCancellableCoroutine { continuation ->
                continuation.invokeOnCancellation {

                }
                api.doAsync(request) {
                    result -> continuation.resume(result)
                }
            }
        }
    }

    scope.launch {
        list.awaitAll()
        onSuccessful()
    }
}