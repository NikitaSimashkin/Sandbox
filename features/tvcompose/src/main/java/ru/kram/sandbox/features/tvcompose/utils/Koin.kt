package ru.kram.sandbox.features.tvcompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.context.GlobalContext
import org.koin.core.scope.Scope

@Composable
inline fun <reified T : Any> rememberScope(): Scope {
    return remember { GlobalContext.get().createScope<T>() }
}