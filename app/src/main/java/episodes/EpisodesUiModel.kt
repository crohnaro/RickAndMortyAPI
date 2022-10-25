package episodes

import domain.mappers.Episode

sealed class EpisodesUiModel {
    class Item (val episode: Episode): EpisodesUiModel()
    class Header (val text: String): EpisodesUiModel()
}
