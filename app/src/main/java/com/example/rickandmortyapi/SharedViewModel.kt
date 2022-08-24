package com.example.rickandmortyapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import com.example.rickandmortyapi.SharedRepository
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {
    private val repository = SharedRepository()

    private val _characterByIdLiveData = MutableLiveData<GenerateCharacterByIdResponse>()
    val characterByIdLiveData : LiveData<GenerateCharacterByIdResponse?> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int){
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)

            _characterByIdLiveData.postValue(response)
        }
    }
}