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

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        val character = repository.getCharacterById(characterId)
        _characterByIdLiveData.postValue(character)
    }
}