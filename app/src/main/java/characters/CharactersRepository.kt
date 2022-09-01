package characters

import com.example.rickandmortyapi.GenerateCharacterByIdResponse

class CharactersRepository {

    suspend fun getCharacterList(pageIndex: Int): List<GenerateCharacterByIdResponse>{
        return emptyList()
    }
}