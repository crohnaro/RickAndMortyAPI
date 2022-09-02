package characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.ModelCharacterListItemBinding
import com.squareup.picasso.Picasso
import epoxy.ViewBindingKotlinModel

class CharacterListPagingEpoxyController: PagedListEpoxyController<GenerateCharacterByIdResponse>() {
    override fun buildItemModel(
        currentPosition: Int,
        item: GenerateCharacterByIdResponse?
    ): EpoxyModel<*> {
        return CharacterGridItemEpoxyModel(item!!.image, item.name).id(item.id)
    }

    data class  CharacterGridItemEpoxyModel(
       val imageUrl: String,
       val name: String
    ): ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item){
        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }
    }
}