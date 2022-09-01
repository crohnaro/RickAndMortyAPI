package characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import com.example.rickandmortyapi.SharedRepository
import com.example.rickandmortyapi.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val viewModel: SharedViewModel,

    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
    ): PageKeyedDataSource<Int, GenerateCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GenerateCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val characterList = repository.getCharacterList(1)
            callback.onResult(characterList, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GenerateCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val characterList = repository.getCharacterList(params.key)
            callback.onResult(characterList,params.key + 1)
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GenerateCharacterByIdResponse>
    ) {
        TODO("Not yet implemented")
    }



}