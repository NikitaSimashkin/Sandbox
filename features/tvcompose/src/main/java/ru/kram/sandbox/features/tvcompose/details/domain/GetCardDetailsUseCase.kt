package ru.kram.sandbox.features.tvcompose.details.domain

import android.net.Uri
import ru.kram.sandbox.features.tvcompose.details.presentation.model.*
import ru.kram.sandbox.features.tvcompose.utils.ImageWrapper

internal class GetCardDetailsUseCase {

    fun getCardDetails(contentId: String): CardDetailsData {
        return CardDetailsData(
            titleItem = TitleItem(
                image = ImageWrapper.Url(MOCK_TITLE_URL),
                title = "Живая сталь",
            ),
            parametersItem = ParametersItem(
                rating = 8.7f,
                genres = listOf("Fantasy", "Drama", "Horror"),
                year = 2022,
                countries = listOf("USA"),
                duration = "45 min per episode",
                policy = Policy.SixteenPlus,
                qualities = listOf(Quality.HD, Quality.FullHD, Quality.UltraHD, Quality.FourK),
            ),
            shortDescription = "A wizard attempting to capture Death to bargain for eternal life traps her younger brother Dream instead.",
            buttons = listOf(
                CardDetailsButton.Watch(
                    text = "Watch Now",
                    hasPlayIcon = true
                ),
                CardDetailsButton.Subscribe(
                    text = "Subscribe to Plus",
                    hasPlayIcon = false
                ),
                CardDetailsButton.AddToFavorites(
                    text = "Add to Favorites"
                ),
                CardDetailsButton.WhereToWatch(
                    text = "Where to Watch"
                ),
                CardDetailsButton.SeasonsAndEpisodes(
                    text = "Seasons & Episodes"
                )
            ),
            trailer = VideoItem(
                uri = Uri.parse(MOCK_TRAILER_URL),
                format = VideoItem.Format.MP4,
                isSoundEnabled = false,
                isLooping = true,
            ),
        )
    }

    companion object {
        private const val MOCK_TITLE_URL = "https://avatars.mds.yandex.net/get-ott/223007/2a0000016ef3ab354044647313b60d3e4393/960x540"
        private const val MOCK_TRAILER_URL = "https://storage.yandexcloud.net/card-details-project/Real%20Steel%20(2011)%20Theatrical%20Trailer%20%5B4K%5D%20%5BFTD-1343%5D.mp4"
    }
}