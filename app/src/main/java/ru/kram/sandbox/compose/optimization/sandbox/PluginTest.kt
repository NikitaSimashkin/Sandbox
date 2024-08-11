package ru.kram.sandbox.compose.optimization.sandbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Unstable(
    var a: Int
)

@Composable
fun TestUnstable(
    unstable: Unstable,
    modifier: Modifier = Modifier
) {

}