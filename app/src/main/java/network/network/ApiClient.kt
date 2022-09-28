package network.network

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import network.network.response.GetEpisodeByIdResponse
import network.network.response.GetEpisodePageResponse
import retrofit2.Response

class ApiClient(
    private val rickandMortyService: RickandMortyService
) {
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GenerateCharacterByIdResponse> {
        return safeApiCall { rickandMortyService.getCharacaterById(characterId) }
    }

    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharacterPageResponse>{
        return safeApiCall { rickandMortyService.getCharacterPage(pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse>{
        return safeApiCall { rickandMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>>{
        return safeApiCall { rickandMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodePage(pageIndex: Int): SimpleResponse<GetEpisodePageResponse>{
        return safeApiCall { rickandMortyService.getEpisodePage(pageIndex) }
    }

    private inline  fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}