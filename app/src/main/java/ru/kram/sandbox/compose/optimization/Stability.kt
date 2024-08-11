package ru.kram.sandbox.compose.optimization

import androidx.compose.runtime.Stable

data class Immutable1(
    val int: Int,
    val str: String,
    val bool: Boolean
)

@Stable
data class Immutable2(
    var int: Int,
    var list: MutableList<Int>
)

@Stable
interface Immutableable


class Immutable3(
    var int: Int
): Immutableable

data class Mutable1(
    var int: Int,
)

data class Mutable2(
    val list: List<Int>
)