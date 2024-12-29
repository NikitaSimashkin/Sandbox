package ru.kram.sandbox.features.tvcompose.details.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.kram.sandbox.features.tvcompose.details.presentation.model.TitleItem
import ru.kram.sandbox.features.tvcompose.utils.ImageWrapper
import ru.kram.sandbox.features.tvcompose.utils.getPainter

@Composable
fun Title(
    titleItem: TitleItem,
    modifier: Modifier = Modifier
) {
    Image(
        painter = titleItem.image.getPainter(),
        contentDescription = null,
        modifier = modifier
            .sizeIn(
                maxWidth = 500.dp,
                maxHeight = 100.dp
            ),
        contentScale = ContentScale.Fit
    )
}

@Preview
@Composable
private fun TitlePreview() {
    Title(
        titleItem = TitleItem(
            title = "Title",
            image = ImageWrapper.Resource(ru.kram.sandbox.common.core.R.drawable.nikita)
        )
    )
}