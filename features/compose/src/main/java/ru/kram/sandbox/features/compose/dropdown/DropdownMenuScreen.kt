package ru.kram.sandbox.features.compose.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kram.sandbox.features.compose.R

@Preview
@Composable
internal fun DropdownMenuScreen() {
    var isPopupExpanded by remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize()) {
        val fallback = painterResource(id = R.drawable.nikita)

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Button(
                onClick = { isPopupExpanded = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Image(
                    imageVector = Icons.Filled.Build,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                DropdownMenu(
                    expanded = isPopupExpanded,
                    onDismissRequest = { isPopupExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {  },
                        text = {
                            Text(text = "2")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {  },
                        text = {
                            Text(text = "2")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {  },
                        text = {
                            Text(text = "2")
                        }
                    )
                }
            }
        }
    }
}