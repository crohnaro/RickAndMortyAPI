package com.example.rickandmortyapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickandMortyService {

    @GET("character/{character-id}")
    fun getCharacaterById(
        @Path("character-id") characterId: Int
    ): Call<GenerateCharacterByIdResponse>
}