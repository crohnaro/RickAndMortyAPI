package com.example.rickandmortyapi

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapi.databinding.ModelCharacterDetailsDataPointBinding
import com.example.rickandmortyapi.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmortyapi.databinding.ModelCharacterDetailsImageBinding
import com.squareup.picasso.Picasso
import epoxy.LoadingEpoxyModel
import epoxy.ViewBindingKotlinModel

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
        if (isLoading){
            LoadingEpoxyModel().id("Loading").addTo(this)
            return
        }

        if (characterResponse == null){
            return
        }

        //Header Model
        HeaderEpoxyModel(
            name = characterResponse!!.name,
            gender = characterResponse!!.gender,
            status = characterResponse!!.status
        ).id("header").addTo(this)

        //Image Model
        ImageEpoxyModel(
            imageUrl = characterResponse!!.image
        ).id("image").addTo(this)


        //Data Point Models
        DataPointEpoxyModel(
            tittle = "Origin",
            description = characterResponse!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            tittle = "Species",
            description = characterResponse!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header){
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            characterName.text = name
            aliveStatus.text = status

            if (gender.equals("male", true)){
                characterGenderImage.setImageResource(R.drawable.ic_male_24)
            } else {
                characterGenderImage.setImageResource(R.drawable.ic_female_24)
            }
        }
    }

    data class ImageEpoxyModel(
        val imageUrl: String
    ):ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image){
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImage);
        }
    }

    data class DataPointEpoxyModel(
        val tittle : String,
        val description : String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point){
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = tittle
            textView.text = description
        }
    }

}