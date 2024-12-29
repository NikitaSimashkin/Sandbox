package ru.kram.sandbox.features.tvcompose.details.presentation.model

data class CardDetailsData(
    val titleItem: TitleItem,
    val parametersItem: ParametersItem,
    val shortDescription: String,
    val buttons: List<CardDetailsButton>,
    val trailer: VideoItem,
)

sealed class CardDetailsButton(
    open val text: String,
    open val backgroundType: BackgroundType
) {
    data class Subscribe(
        override val text: String,
        val hasPlayIcon: Boolean
    ) : CardDetailsButton(text, BackgroundType.Plus)

    data class Watch(
        override val text: String,
        val hasPlayIcon: Boolean
    ) : CardDetailsButton(text, BackgroundType.Normal)

    data class AddToFavorites(
        override val text: String
    ) : CardDetailsButton(text, BackgroundType.Normal)

    data class WhereToWatch(
        override val text: String
    ) : CardDetailsButton(text, BackgroundType.Normal)

    data class SeasonsAndEpisodes(
        override val text: String
    ) : CardDetailsButton(text, BackgroundType.Normal)
}

enum class BackgroundType {
    Plus,
    Normal
}
