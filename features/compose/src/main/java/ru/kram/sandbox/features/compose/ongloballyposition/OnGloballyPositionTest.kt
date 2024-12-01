package ru.kram.sandbox.features.compose.ongloballyposition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
internal fun OnGloballyPositionTest() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    println("1: ${it.size.height}")
                }
                .size(100.dp)
                .background(Color.Red)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .onGloballyPositioned {
                    println("2: ${it.size.height}")
                }
                .background(Color.Green)
        )
    }
}