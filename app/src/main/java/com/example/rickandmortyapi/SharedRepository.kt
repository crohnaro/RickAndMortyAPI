package com.example.rickandmortyapi

import domain.mappers.CharacterMapper
import domain.models.Character
import network.network.NetworkLayer

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if(request.failed){
            return null
        }

        if (!request.isSuccessful){
            return null
        }
        return CharacterMapper.buildFrom(response = request.body)
    }
}
