package ru.kram.sandbox.features.compose.optimization.nonrestartable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ru.kram.sandbox.features.compose.R
import kotlin.random.Random

@NonRestartableComposable
@NonSkippableComposable
@Composable
internal fun NonRestartableDemo() {
    Box {}
    Icon(painter = TODO(), contentDescription = "")
    Image(painter = TODO(), contentDescription = null)
}

@Composable
internal fun NonRestartableScreen() {

    Column(
        modifier = Modifier.fillMaxSize().padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        var alpha by remember { mutableStateOf(1f) }

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.nikita),
            contentDescription = null,
            alpha = alpha
        )

        Button(
            onClick = {
                alpha = Random.nextFloat()
            }
        ) {
            Text(text = "Change alpha to $alpha")
        }
    }
}