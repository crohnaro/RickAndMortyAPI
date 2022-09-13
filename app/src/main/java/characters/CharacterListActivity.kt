package characters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapi.*

class CharacterListActivity: AppCompatActivity() {

    private val epoxyController = CharacterListPagingEpoxyController (::onCharacterSelected)

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        viewModel.charactersPagedListLiveData.observe(this){ pagedList ->
            epoxyController.submitList(pagedList)
        }

        findViewById<EpoxyRecyclerView>(R.id.epoxyRecycerView).setController(epoxyController)
    }

    private fun onCharacterSelected(charactedId: Int){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID, charactedId)
        startActivity(intent)
    }

}