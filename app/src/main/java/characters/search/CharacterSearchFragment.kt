package characters.search


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.example.rickandmortyapi.R
import com.example.rickandmortyapi.databinding.FragmentCharacterSearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharacterSearchFragment : Fragment(R.layout.fragment_character_search) {
    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding get() = _binding!!

    private var currentText = ""
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        viewModel.submitQuery(currentText)
    }

    private val viewModel: CharacterSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCharacterSearchBinding.bind(view)

        val epoxyController = CharacterSearchEpoxyController { characterId ->
            //todo
        }

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        binding.searchEditText.doAfterTextChanged {
            currentText = it?.toString() ?: ""

            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, 500L)
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                epoxyController.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}