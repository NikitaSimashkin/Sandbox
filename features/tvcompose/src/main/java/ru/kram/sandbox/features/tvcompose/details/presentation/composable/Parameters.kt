package ru.kram.sandbox.features.tvcompose.details.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import ru.kram.sandbox.features.tvcompose.details.presentation.model.ParametersItem
import ru.kram.sandbox.features.tvcompose.details.presentation.model.Policy
import ru.kram.sandbox.features.tvcompose.details.presentation.model.Quality
import ru.kram.sandbox.features.tvcompose.details.presentation.theme.CardDetailsTheme
import ru.kram.sandbox.features.tvcompose.details.presentation.theme.Colors
import ru.kram.sandbox.features.tvcompose.details.presentation.theme.Typography
import ru.kram.sandbox.features.tvcompose.utils.TvPreview
import java.util.Locale

@Composable
fun Parameters(
    parametersItem: ParametersItem,
    modifier: Modifier = Modifier,
) {
    val textStyle = Typography.bodyPrimary

    val separator = @Composable {
        Text(
            text = AnnotatedString(" • "),
            style = textStyle,
            color = Colors.textSecondary,
        )
    }

    Row(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(all = 4.dp)
        ) {
            // rating
            Text(
                text = parametersItem.rating.toString(),
                style = textStyle,
                color = getRatingColor(parametersItem.rating),
            )
            separator()

            // genres
            Text(
                text = parametersItem.genres.joinToString(separator = ", ")
                    .replaceFirstChar { it.titlecase(Locale.ROOT) },
                style = textStyle,
                color = Colors.textPrimary,
            )
            separator()

            // year
            Text(
                text = parametersItem.year.toString(),
                style = textStyle,
                color = Colors.textPrimary,
            )
            separator()

            // countries
            Text(
                text = parametersItem.countries.joinToString(separator = ", ") {
                    it.replaceFirstChar {
                        it.titlecase(
                            Locale.ROOT
                        )
                    }
                },
                style = textStyle,
                color = Colors.textPrimary,
            )
            separator()

            // duration
            Text(
                text = parametersItem.duration,
                style = textStyle,
                color = Colors.textPrimary,
            )
            separator()

            // policy
            Text(
                text = parametersItem.policy.ageRestriction,
                style = textStyle,
                color = Colors.textPrimary,
            )
        }

        // quality
        Quality(
            qualities = parametersItem.qualities,
            textStyle = textStyle,
        )
    }
}

@Composable
private fun Quality(
    qualities: List<Quality>,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val bestQuality = remember { qualities.getBestQuality() }
    val text = when (bestQuality) {
        Quality.HD -> "HD"
        Quality.FullHD -> "FullHD"
        Quality.UltraHD -> "UltraHD"
        Quality.FourK -> "4K"
    }

    Text(
        text = text,
        style = textStyle,
        color = Colors.textPrimary,
        modifier = modifier
            .padding(start = 8.dp)
            .background(color = Colors.background)
            .padding(all = 4.dp),
    )
}

@Composable
private fun getRatingColor(rating: Float) = when {
    rating >= 8.0 -> Colors.textSuccess
    rating >= 6.0 -> Colors.textWarning
    else -> Colors.textError
}

private fun List<Quality>.getBestQuality(): Quality {
    val qualityPriority = listOf(Quality.HD, Quality.FullHD, Quality.UltraHD, Quality.FourK)
    return maxByOrNull { qualityPriority.indexOf(it) } ?: Quality.HD
}

@TvPreview
@Composable
private fun ParametersPreview() {
    CardDetailsTheme {
        Box(modifier = Modifier.background(Color.LightGray)) {
            val item = ParametersItem(
                rating = 8.5f,
                genres = listOf("action", "comedy"),
                year = 2021,
                countries = listOf("usa", "russia"),
                duration = "1h 30m",
                policy = Policy.TwelvePlus,
                qualities = listOf(Quality.HD, Quality.FourK),
            )

            Parameters(
                parametersItem = item,
            )
        }
    }
}
