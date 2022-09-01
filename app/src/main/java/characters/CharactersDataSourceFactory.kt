package characters

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope


class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
): androidx.paging.DataSource.Factory<Int, GenerateCharacterByIdResponse>(){
    override fun create(): androidx.paging.DataSource<Int, GenerateCharacterByIdResponse> {
        return CharactersDataSource(coroutineScope, repository)
    }
}