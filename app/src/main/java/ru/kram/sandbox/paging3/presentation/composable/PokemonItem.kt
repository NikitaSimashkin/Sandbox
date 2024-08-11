package ru.kram.sandbox.paging3.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.kram.sandbox.R
import ru.kram.sandbox.paging3.presentation.model.PokemonUiModel
import java.util.Locale

@Composable
fun PokemonItem(
    pokemon: PokemonUiModel?,
    modifier: Modifier = Modifier
) {
    val pokemonOrDefault = pokemon ?: PokemonUiModel.Default

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = pokemonOrDefault.imageUrl ?: R.drawable.nikita,
            contentDescription = pokemonOrDefault.name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "id: ${pokemonOrDefault.id}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "name: ${pokemonOrDefault.name.replaceFirstChar { it.uppercaseChar() }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun PokemonPreview() {
    PokemonItem(
        pokemon = PokemonUiModel(
            id = 1,
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
        )
    )
}