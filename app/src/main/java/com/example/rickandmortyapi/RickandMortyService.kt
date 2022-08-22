package com.example.rickandmortyapi

import retrofit2.Call
import retrofit2.http.GET

interface RickandMortyService {

    @GET("character/2")
    fun getCharacaterById(): Call<Any>
}