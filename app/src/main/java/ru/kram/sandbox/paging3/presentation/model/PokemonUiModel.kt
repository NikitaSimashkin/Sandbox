package ru.kram.sandbox.paging3.presentation.model

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String?
) {
    companion object {
        val Default = PokemonUiModel(
            id = -1,
            name = "Nikita",
            imageUrl = null
        )
    }
}