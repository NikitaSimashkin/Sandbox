package ru.kram.sandbox.features.tvcompose.details.presentation.model

data class ParametersItem(
    val rating: Float,
    val genres: List<String>,
    val year: Int,
    val countries: List<String>,
    val duration: String,
    val policy: Policy,
    val qualities: List<Quality>,
)

sealed class Policy(val ageRestriction: String) {
    data object General : Policy("0+")
    data object TwelvePlus : Policy("12+")
    data object SixteenPlus : Policy("16+")
    data object EighteenPlus : Policy("18+")
}

enum class Quality {
    HD, FullHD, UltraHD, FourK
}