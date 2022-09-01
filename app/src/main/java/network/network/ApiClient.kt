package network.network

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
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

    private inline  fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}