package com.example.rickandmortyapi

import com.airbnb.epoxy.EpoxyController

class CharacterDetailsEpoxyController: EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field=value
            if (field){
                requestModelBuild()
            }
        }

    var characterResponse: GenerateCharacterByIdResponse? = null
        set(value) {
            field = value
            if (field != null){
                isLoading = false
                requestModelBuild()
            }
        }
    override fun buildModels() {
        TODO("Not yet implemented")
    }
}