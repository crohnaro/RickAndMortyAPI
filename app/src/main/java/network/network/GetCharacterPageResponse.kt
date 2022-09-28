package network.network

import android.icu.text.IDNA
import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import network.network.response.PageInfo

class GetCharacterPageResponse(
    val info: PageInfo = PageInfo(),
    val results :List<GenerateCharacterByIdResponse> = emptyList()
)