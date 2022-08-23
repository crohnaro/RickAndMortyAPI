package com.example.rickandmortyapi

import retrofit2.Response

class ApiClient(
    private val rickandMortyService: RickandMortyService
) {
    suspend fun getCharacterById(characterId: Int): Response<GenerateCharacterByIdResponse>{
        return rickandMortyService.getCharacaterById(characterId)
    }
}