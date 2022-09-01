package network.network

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickandMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacaterById(
        @Path("character-id") characterId: Int
    ): Response<GenerateCharacterByIdResponse>

    @GET("character")
    suspend fun getCharacterPage(
        @Query("page")pageIndex:Int
    ): Response<GetCharacterPageResponse>
}