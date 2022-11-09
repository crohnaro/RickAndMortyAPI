package episodes

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ModelCharacterListItemSquareBinding
import com.squareup.picasso.Picasso
import epoxy.ViewBindingKotlinModel

class EpisodeDetailEpoxyController(
  private val characterList: List<domain.models.Character>
): EpoxyController() {

    override fun buildModels() {
        characterList.forEach { character ->
            CharacterEpoxyModel(
                imageUrl = character.image,
                name = character.name
            ).id(character.id).addTo(this)
        }
    }

    data class CharacterEpoxyModel(
        val imageUrl: String,
        val name: String
    ) : ViewBindingKotlinModel<ModelCharacterListItemSquareBinding>(
        R.layout.model_character_list_item_square
    ) {
        override fun ModelCharacterListItemSquareBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }
    }
}