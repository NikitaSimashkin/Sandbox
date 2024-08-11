package ru.kram.sandbox.paging3.data.network

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