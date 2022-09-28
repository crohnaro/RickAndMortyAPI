package episodes

import network.network.NetworkLayer
import network.network.response.GetEpisodePageResponse

class EpisodeRepository {
    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodePageResponse?{
        val pageRequest = NetworkLayer.apiClient.getEpisodePage(pageIndex)

        if (!pageRequest.isSuccessful){
            return null
        }

        return pageRequest.body
    }
}