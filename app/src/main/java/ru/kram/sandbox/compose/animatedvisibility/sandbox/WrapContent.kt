package ru.kram.sandbox.compose.animatedvisibility.sandbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun WrapContent() {
	Box(
		modifier = Modifier
			.width(IntrinsicSize.Max)
			.background(Color.Black)
	) {
		Column(
			modifier = Modifier
				.background(Color.White)
		) {
			Text(
				text = "123",
				modifier = Modifier
					.fillMaxWidth()
					.background(Color.Red)
			)
			Text(
				text = "Hello, world",
				modifier = Modifier
					.background(Color.Green)
			)
		}
	}
}