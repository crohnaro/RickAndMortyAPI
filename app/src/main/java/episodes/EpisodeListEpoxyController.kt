package episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ModelEpisodeListItemBinding
import com.example.rickandmortyapi.databinding.ModelEpisodeListTittleBinding
import domain.mappers.Episode
import epoxy.ViewBindingKotlinModel
import kotlinx.coroutines.ObsoleteCoroutinesApi

@OptIn(ObsoleteCoroutinesApi::class)
class EpisodeListEpoxyController: PagingDataEpoxyController<EpisodesUiModel>() {

    override fun buildItemModel(currentPosition: Int, item: EpisodesUiModel?): EpoxyModel<*> {
        return when (item!!) {
            is EpisodesUiModel.Item -> {
                val episode = ( item as EpisodesUiModel.Item ).episode
                EpisodeListItemEpisodeEpoxyModel(
                    episode = episode,
                    onClick = { episodeId ->

                    }
                ).id("episode_${episode.id}")
            }

            is EpisodesUiModel.Header -> {
                val headerText = ( item as EpisodesUiModel.Header ).text
                EpisodeListTittleEpoxyModel(headerText).id("header_$headerText")
            }
        }
    }

    data class EpisodeListItemEpisodeEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item){
        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.getFormattedSeasonTruncated()

            root.setOnClickListener { onClick(episode.id)}
        }

    }

    data class EpisodeListTittleEpoxyModel(
        val tittle: String
    ) : ViewBindingKotlinModel<ModelEpisodeListTittleBinding>(R.layout.model_episode_list_tittle){
        override fun ModelEpisodeListTittleBinding.bind(){
            textView.text = tittle
        }
    }


}