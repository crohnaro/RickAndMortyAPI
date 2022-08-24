package network.network

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickandMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacaterById(
        @Path("character-id") characterId: Int
    ): Response<GenerateCharacterByIdResponse>
}