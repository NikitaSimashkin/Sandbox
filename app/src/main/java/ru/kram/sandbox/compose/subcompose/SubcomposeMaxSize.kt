package ru.kram.sandbox.compose.subcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SubcomposeMaxSize(modifier: Modifier = Modifier) {
    SubcomposeLayout(
        modifier = modifier.background(Color.Red)
    ) { constraints ->
        val measurables = subcompose(0) {
            Text(text = "Nikita", modifier = Modifier.background(Color.Green))
            Text(text = "Simashkin", modifier = Modifier.background(Color.Blue))
            Text(text = "Andreevich", modifier = Modifier.background(Color.Cyan))
        }

        layout(width = constraints.maxWidth, height = constraints.maxHeight) {

            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            var y = 0
            placeables.forEach { placeable ->
                placeable.place(0, y)
                y += placeable.height
            }
        }
    }
}

@Composable
fun SubcomposeMinSize(modifier: Modifier = Modifier) {
    SubcomposeLayout(
        modifier = modifier.background(Color.Red).size(100.dp)
    ) { constraints ->
        val measurables = subcompose(0) {
            Text(text = "Nikita", modifier = Modifier.background(Color.Green))
            Text(text = "Simashkin", modifier = Modifier.background(Color.Blue))
            Text(text = "Andreevich", modifier = Modifier.background(Color.Cyan))
        }

        layout(width = constraints.minWidth, height = constraints.maxHeight) {
            // constraints = 100.dp поэтому первый текст занимает все место
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            var y = 0
            placeables.forEach { placeable ->
                placeable.place(0, y)
                y += placeable.height
            }
        }
    }
}

@Preview
@Composable
private fun SubcomposeMaxSizePreview() {
    SubcomposeMaxSize()
}

@Preview
@Composable
private fun SubcomposeMinSizePreview() {
    SubcomposeMinSize()
}