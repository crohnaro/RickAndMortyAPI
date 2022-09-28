package network.network.response

import com.example.rickandmortyapi.GenerateCharacterByIdResponse

class GetEpisodePageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = emptyList()
)