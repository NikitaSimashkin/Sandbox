package ru.kram.sandbox.features.compose.optimization.sandbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

internal class Unstable(
    var a: Int
)

@Composable
internal fun TestUnstable(
    unstable: Unstable,
    modifier: Modifier = Modifier
) {

}