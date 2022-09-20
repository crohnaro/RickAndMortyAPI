package com.example.rickandmortyapi

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import characters.CharacterListPagingEpoxyController
import characters.CharactersViewModel
import com.airbnb.epoxy.EpoxyRecyclerView


class CharacterListFragment : Fragment() {

    private val epoxyController = CharacterListPagingEpoxyController (::onCharacterSelected)

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this)[CharactersViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.charactersPagedListLiveData.observe(viewLifecycleOwner){ pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxyRecycerView).setController(epoxyController)

    }

    private fun onCharacterSelected(characterId: Int){
       val directions =  CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
            characterId = characterId
        )
        findNavController().navigate(directions)
    }


}