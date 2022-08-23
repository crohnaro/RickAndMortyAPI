package com.example.rickandmortyapi

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): GenerateCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.isSuccessful){
            return request.body()!!
        }

        return null
    }
}
