package ru.kram.sandbox.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.StringBuilder
import kotlin.math.max
import kotlin.math.min

@Preview
@Composable
private fun TestWeight() {
    Row(
        Modifier
            .background(Color.White)
            .width(200.dp)) {
        Text(
            text =
            "aaaaaaaaasasasasasas" +
                    "asasasasasasasasasasasasasasa" +
                    "sasasaaaaaaaaaasasasasasasasasasa" +
                    "sasasasasasasasasasasasasasa12323",
            maxLines = 1,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
private fun TestWeight2() {
    Row(
        Modifier
            .background(Color.White)
            .width(200.dp)) {
        Icon(
            imageVector = Icons.Default.AccountBox,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = "123333333",
            maxLines = 1,
            modifier = Modifier
                .weight(1f),
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "123",
            maxLines = 1,
            modifier = Modifier
                .weight(1f, fill = false),
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "123",
            maxLines = 1,
            modifier = Modifier
                .weight(1f),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TestAlign() {
    Row(
        Modifier
            .background(Color.White)
            .width(400.dp)
            .height(50.dp)) {
        Text(
            text = "123",
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.Bottom),
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "456",
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = "789",
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.Top),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TestAlignBy() {
    Row(
        Modifier
            .background(Color.White)
            .width(400.dp)
            .height(50.dp)) {
        Text(
            text = "123",
            maxLines = 1,
            modifier = Modifier
                .width(100.dp)
                .alignBy {
                    it.measuredHeight / 2
                },
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "456",
            maxLines = 1,
            modifier = Modifier
                .width(100.dp)
                .alignBy {
                    it.measuredHeight / 2
                },
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = "789",
            maxLines = 1,
            modifier = Modifier
                .width(100.dp)
                .alignBy {
                    it.measuredHeight / 2
                },
            fontSize = 25.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TextAlignByBaseline() {
    Row(
        Modifier
            .background(Color.White)
            .width(400.dp)
            .height(50.dp)) {
        Text(
            text = "123",
            maxLines = 1,
            modifier = Modifier
                .width(100.dp)
                .alignByBaseline(),
            fontSize = 15.sp,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "456",
            maxLines = 1,
            modifier = Modifier
                .width(100.dp)
                .alignByBaseline(),
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = "789",
            maxLines = 1,
            modifier = Modifier
                .width(100.dp)
                .alignByBaseline(),
            fontSize = 25.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
private fun TestFillMaxWidthText() {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "123",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
private fun TestWrapContentText() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            text = "123",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
private fun TestIntricticsMinText() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        Text(
            text = "123",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
fun Test2TextWrapContent() {
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

@Preview
@Composable
fun TestLayout() {

    @Composable
    fun SomeButton() {
        Button(
            onClick = { /* do something */ },
            modifier = Modifier.background(Color.Blue)
        ) {
            Text("Click meeeeeeeeeeeeeeeeeeeeeee")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                layout(100.dp.roundToPx(), 100.dp.roundToPx()) {
                    placeables.forEach { it.place(0, 0) }
                }
            },
            content = {
                SomeButton()
            },
            modifier = Modifier
                .clipToBounds()
                .background(Color.Green)
        )
    }
}
