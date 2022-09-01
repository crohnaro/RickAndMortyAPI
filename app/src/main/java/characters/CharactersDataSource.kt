package characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import com.example.rickandmortyapi.SharedRepository
import com.example.rickandmortyapi.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
    ): PageKeyedDataSource<Int, GenerateCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GenerateCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharacterPage(1)
            if (page == null){
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.results, null, getPageIndexFromNext(page.info.next))
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GenerateCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharacterPage(params.key)
            if (page == null){
                callback.onResult(emptyList(), null)
                return@launch
            }

            callback.onResult(page.results, getPageIndexFromNext(page.info.next) )
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GenerateCharacterByIdResponse>
    ) {
        TODO("Not yet implemented")
    }

    private fun getPageIndexFromNext(next: String?): Int?{
        return next?.split("?page=")?.get(1)?.toInt()
    }



}