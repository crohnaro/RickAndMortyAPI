package com.example.rickandmortyapi

import domain.mappers.CharacterMapper
import domain.models.Character
import network.network.NetworkLayer
import network.network.response.GetEpisodeByIdResponse

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if(request.failed || !request.isSuccessful){
            return null
        }
        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)
        return CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )
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
