package ru.kram.pokemonpaging.data.network

import kotlinx.serialization.Serializable

@Serializable
class PokemonDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
) {

    @Serializable
    data class Result(
        val name: String,
        val url: String
    )
}