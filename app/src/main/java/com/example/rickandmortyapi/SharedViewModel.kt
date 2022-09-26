package com.example.rickandmortyapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import com.example.rickandmortyapi.SharedRepository
import domain.models.Character
import kotlinx.coroutines.launch
import network.network.SimpleMortyCache

class SharedViewModel: ViewModel() {
    private val repository = SharedRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData : LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int){

        //Verificar se o personagem esta em cache
        val cachedCharacter = SimpleMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            _characterByIdLiveData.postValue(cachedCharacter)
            return
        }

        //Caso contrario, fazer a call na api
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)

            _characterByIdLiveData.postValue(response)

            response?.let {
                SimpleMortyCache.characterMap[characterId] = it
            }

        }
    }
}