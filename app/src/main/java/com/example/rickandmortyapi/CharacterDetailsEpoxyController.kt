package com.example.rickandmortyapi

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapi.databinding.*
import com.squareup.picasso.Picasso
import domain.mappers.Episode
import domain.models.Character
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

    var character: Character? = null
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

        if (character == null){
            return
        }

        //Header Model
        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)

        //Image Model
        ImageEpoxyModel(
            imageUrl = character!!.image
        ).id("image").addTo(this)

        if (character!!.episodeList.isNotEmpty()){
            val items = character!!.episodeList.map {
                EpisodeCarouselItemEpoxyModel(it).id(it.id)
            }
            TitleEpoxyModel(title = "Episodes").id("title_episodes").addTo(this)
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.15f)
                .addTo(this)
        }


        //Data Point Models
        DataPointEpoxyModel(
            tittle = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            tittle = "Species",
            description = character!!.species
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
    data class EpisodeCarouselItemEpoxyModel(
        val episode: Episode
    ): ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item){

        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodeTextView.text = episode.getFormattedSeasonTruncated()
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
        }
    }

    data class TitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title) {

        override fun ModelTitleBinding.bind() {
            titleTextView.text = title
        }
    }

}