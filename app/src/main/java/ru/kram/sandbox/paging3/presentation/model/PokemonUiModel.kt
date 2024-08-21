package ru.kram.sandbox.paging3.presentation.model

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String?,
) {
    companion object {
        const val INVALID_ID = -1

        val Default = PokemonUiModel(
            id = INVALID_ID,
            name = "Nikita",
            imageUrl = null
        )
    }
}