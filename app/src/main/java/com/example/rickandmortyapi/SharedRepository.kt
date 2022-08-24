package com.example.rickandmortyapi

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): GenerateCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if(request.failed){
            return null
        }

        if (!request.isSuccessful){
            return null
        }
        return request.body
    }
}
