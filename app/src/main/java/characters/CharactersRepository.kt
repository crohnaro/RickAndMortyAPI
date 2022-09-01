package characters

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import network.network.GetCharacterPageResponse
import network.network.NetworkLayer

class CharactersRepository {

    suspend fun getCharacterPage(pageIndex: Int): GetCharacterPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful){
            return null
        }
        return request.body
    }
}