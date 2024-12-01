package ru.kram.pokemonpaging.presentation.model

data class PokemonScreenState(
    val pokemonsDbCount: Int,
    val pageInfo: PagesInfo
)

data class PagesInfo(
    val pages: List<Page>
)

data class Page(
    val value: Int,
    val isEnabled: Boolean
)
