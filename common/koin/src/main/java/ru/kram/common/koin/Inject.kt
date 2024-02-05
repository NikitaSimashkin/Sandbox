package ru.kram.common.koin

import android.content.ComponentCallbacks
import org.koin.android.ext.android.inject

inline fun <reified T : Any> ComponentCallbacks.injectUnsafe(): Lazy<T> {
	return inject(mode = LazyThreadSafetyMode.NONE)
}