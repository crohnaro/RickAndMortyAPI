package episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickandmortyapi.Constants
import domain.mappers.Episode
import kotlinx.coroutines.flow.map

class EpisodesViewModel: ViewModel() {

    private val repository = EpisodeRepository()
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(repository)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { model: EpisodesUiModel?, model2: EpisodesUiModel? ->
            return@insertSeparators null
        }
    }

}