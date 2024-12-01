package ru.kram.pokemonpaging.presentation.composable

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.kram.pokemonpaging.R
import ru.kram.pokemonpaging.presentation.model.PokemonUiModel

@Composable
fun PokemonItem(
    pokemon: PokemonUiModel?,
    onDelete: (PokemonUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val pokemonOrDefault = pokemon ?: PokemonUiModel.Default

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val fallback = painterResource(id = R.drawable.nikita)
        AsyncImage(
            model = pokemonOrDefault.imageUrl ?: R.drawable.nikita,
            contentDescription = pokemonOrDefault.name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp)),
            error = fallback,
            fallback = fallback,
            placeholder = painterResource(id = R.drawable.placeholder)
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

        IconButton(
            onClick = {
                if (pokemon != null && pokemon.id != PokemonUiModel.INVALID_ID) {
                    onDelete(pokemon)
                }
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.error
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
        ),
        onDelete = {}
    )
}