package network.network

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import network.network.response.GetEpisodeByIdResponse
import network.network.response.GetEpisodePageResponse
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

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeRange: String
    ): Response<List<GetEpisodeByIdResponse>>

    @GET("episode/")
    suspend fun getEpisodePage(
        @Query("page")pageIndex: Int
    ): Response<GetEpisodePageResponse>
}