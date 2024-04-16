package ru.kram.sandbox.compose.animatedvisibility.sandbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LayoutTest() {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Text(
			text = "200dp",
			modifier = Modifier
				.width(200.dp)
				.background(Color.Red)
		)
		Layout(
			measurePolicy = { measurables, constraints ->
				val placeables = measurables.map { measurable ->
					measurable.measure(constraints.copy(maxWidth = 1000))
				}
				layout(constraints.minWidth, constraints.minHeight) {
					placeables.forEach { it.place(0, 0) }
				}
			},
			content = {
				SomeButton()
			},
			modifier = Modifier
				.wrapContentSize()
				.background(Color.Green)
		)
	}
}

@Composable
@Preview
fun SomeButton() {
	Button(
		onClick = { /* do something */ },
		modifier = Modifier.background(Color.Blue).width(200.dp)
	) {
		Text("Click me")
	}
}