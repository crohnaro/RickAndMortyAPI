package com.example.rickandmortyapi

import domain.mappers.CharacterMapper
import domain.models.Character
import network.network.NetworkLayer
import network.network.SimpleMortyCache
import network.network.response.GetEpisodeByIdResponse

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Character? {

        val cachedCharacter = SimpleMortyCache.characterMap[characterId]
        if (cachedCharacter != null){
            return cachedCharacter
        }
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if(request.failed || !request.isSuccessful){
            return null
        }
        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)
        val character =  CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )

        SimpleMortyCache.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GenerateCharacterByIdResponse
    ): List<GetEpisodeByIdResponse>{
        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if(request.failed || !request.isSuccessful){
            return emptyList()
        }

        return request.body
    }
}
