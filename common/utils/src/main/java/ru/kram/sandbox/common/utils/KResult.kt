package ru.kram.sandbox.common.utils

sealed class KResult<out T, out E: Throwable> {
    data class Success<T>(val data: T) : KResult<T, Nothing>()
    data class Error<E: Throwable>(val exception: E) : KResult<Nothing, E>()
}