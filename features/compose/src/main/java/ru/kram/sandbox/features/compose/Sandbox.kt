package ru.kram.sandbox.features.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Stack

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
private fun Test2TextWrapContent() {
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
private fun TestLayout() {

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

@Composable
private fun ListWithKeyAndTypeTest() {
    val messages = listOf(1, 2, 3)
    LazyColumn {
        items(
            items = messages,
            key = { message -> message }, // for optimize
            contentType = { message -> message::class.java.simpleName } // for reuse
        ) {
            Text(text = it.toString())
        }
    }
}

@Composable
@Preview
private fun MovableContentTest() {
    val items = movableContentOf {
        Text("1")
        Text("2")
        Text("3")
    }

    if (true) {
        Row {
            items()
        }
    } else {
        Column {
            items()
        }
    }

    key(1) {
        Text("4")
    }
}

@Preview
@Composable
private fun FillMaxSizeTest() {
    Box(
        modifier = Modifier.size(200.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        )
        Text(text = "Nikita")
    }
}

@Preview
@Composable
private fun MatchParentSizeTest() {
    Box(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red)
        )
        Text(text = "Nikita")
    }
}

@Preview
@Composable
private fun RequiredSizeTest() {
    Box(
        modifier = Modifier
            .background(Color.Red)
            .size(100.dp)
            .background(Color.Green)
            .requiredSize(50.dp)
    )
}

@Preview
@Composable
private fun RequiredSizeTest2() {
    // Layout идут сначала вниз потом вверх, а вот
    // Drawing сразу только вниз
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Red)
            .requiredSize(50.dp)
            .background(Color.Green)
    )
}

@Preview
@Composable
private fun WrapContentSizeTest() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Green)
            .wrapContentSize(align = Alignment.CenterStart)
            .background(Color.Red)
    ) {
        Text(
            text = "Nikita",
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun ClipSizeTest() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Green)
            .padding(10.dp)
            .background(Color.Red)
            .size(200.dp)
            .clip(CircleShape)
            .background(Color.Blue)
    )
}

@Preview
@Composable
private fun CircleImageTest() {
    Image(
        painter = painterResource(id = R.drawable.carousel2_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .background(Color.Green)
            .clip(RoundedCornerShape(40.dp))
            .size(200.dp)
    )
    Stack<Int>().pop()
}

@Preview
@Composable
private fun OnGloballyPositioned() {
    Box(
        modifier = Modifier
            .background(Color.Green)
            .onGloballyPositioned {
                println("onGloballyPositioned: ${it.size.height}")
            }
            .height(100.dp)

    ) {
        Text(
            text = "Nikita",
            modifier = Modifier
        )
    }
}