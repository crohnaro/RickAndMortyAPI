package episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ModelEpisodeListItemBinding
import domain.mappers.Episode
import epoxy.ViewBindingKotlinModel
import kotlinx.coroutines.ObsoleteCoroutinesApi

@OptIn(ObsoleteCoroutinesApi::class)
class EpisodeListEpoxyController: PagingDataEpoxyController<Episode>() {

    override fun buildItemModel(currentPosition: Int, item: Episode?): EpoxyModel<*> {
        return EpisodeListItemEpisodeEpoxyModel(
            episode = item!!,
            onClick = { episodeId ->

            }
        ).id("episode_${item.id}")
    }

    data class EpisodeListItemEpisodeEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item){
        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.episode

            root.setOnClickListener { onClick(episode.id)}


        }

    }
}