package ru.kram.sandbox.features.compose.optimization

import androidx.compose.runtime.Stable

internal data class Immutable1(
    val int: Int,
    val str: String,
    val bool: Boolean
)

@Stable
internal data class Immutable2(
    var int: Int,
    var list: MutableList<Int>
)

@Stable
internal interface Immutableable


internal class Immutable3(
    var int: Int
): Immutableable

internal data class Mutable1(
    var int: Int,
)

internal data class Mutable2(
    val list: List<Int>
)