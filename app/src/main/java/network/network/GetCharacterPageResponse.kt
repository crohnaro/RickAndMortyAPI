package network.network

import android.icu.text.IDNA
import com.example.rickandmortyapi.GenerateCharacterByIdResponse

class GetCharacterPageResponse(
    val info: Info = Info(),
    val results :List<GenerateCharacterByIdResponse> = emptyList()
) {
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}