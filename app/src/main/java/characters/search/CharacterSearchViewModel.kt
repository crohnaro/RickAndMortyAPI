package characters.search

import android.media.metrics.Event
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmortyapi.Constants
import episodes.EpisodePagingSource
import okhttp3.internal.userAgent


class CharacterSearchViewModel: ViewModel() {
    private var currentUserSearch: String = ""
    private var pagingSource: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true) {
                field = CharacterSearchPagingSource(currentUserSearch){ localException ->

                    _localExceptionEventLiveData.postValue(com.example.rickandmortyapi.Event(localException))
                }
            }

            return field
        }
    val flow = Pager(
        //Configure how data is loaded by passing additional properties to
        //PagingConfig, such as prefetchDistance
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    private val _localExceptionEventLiveData = MutableLiveData<com.example.rickandmortyapi.Event<CharacterSearchPagingSource.LocalException>>()
    val localExceptionEventLiveData: LiveData<com.example.rickandmortyapi.Event<CharacterSearchPagingSource.LocalException>> = _localExceptionEventLiveData


    fun submitQuery(userSearch: String){
        currentUserSearch = userSearch
        pagingSource?.invalidate()
    }

}