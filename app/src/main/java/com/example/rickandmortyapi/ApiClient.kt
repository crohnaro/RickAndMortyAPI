package com.example.rickandmortyapi

import retrofit2.Response

class ApiClient(
    private val rickandMortyService: RickandMortyService
) {
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GenerateCharacterByIdResponse>{
        return safeApiCall { rickandMortyService.getCharacaterById(characterId) }
    }

    private inline  fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T>{
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}