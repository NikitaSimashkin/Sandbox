package ru.kram.pokemonpaging.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kram.pokemonpaging.presentation.model.Page
import ru.kram.pokemonpaging.presentation.model.PagesInfo

@Composable
fun Pages(
    rows: Int,
    columns: Int,
    pagesInfo: PagesInfo,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        (0 until rows).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.fillMaxWidth().weight(1f, false)
            ) {
                (0 until columns).forEach { column ->
                    val index = row * columns + column
                    val page = if (index < pagesInfo.pages.size) pagesInfo.pages[index] else null
                    key(page) {
                        if (page != null) {
                            PageButton(
                                page = page,
                                onClick = onClick,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PageButton(
    page: Page,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = { onClick(page.value) },
        enabled = page.isEnabled,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = page.value.toString(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
