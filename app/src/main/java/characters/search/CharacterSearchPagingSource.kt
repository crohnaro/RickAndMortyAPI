package characters.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import domain.mappers.CharacterMapper
import domain.models.Character
import network.network.NetworkLayer
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class CharacterSearchPagingSource(
    private val userSearch: String,
    private val localExceptionCallback: (LocalException) ->Unit
) : PagingSource<Int, domain.models.Character>() {

    sealed class LocalException(
        val tittle: String,
        val description: String = ""
    ): Exception(){
        object EmptySearch : LocalException(
            tittle = "Comece a digitar para procurar!   "
        )
        object NoResults : LocalException(
            tittle = "Opa",
            description = "Parece que não encontramos nada :("
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, domain.models.Character>{

        if (userSearch.isEmpty()){
            val exception = LocalException.EmptySearch
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1 ) null else pageNumber - 1

        val request = NetworkLayer.apiClient.getCharactersPage(
            characterName = userSearch,
            pageIndex = pageNumber
        )

        if (request.data?.code() == 404){
            val exception = LocalException.NoResults
            localExceptionCallback(exception)
            return LoadResult.Error(exception)
        }

        request.exception?.let{
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = request.bodyNullable?.results?.map { characterResponse ->
                CharacterMapper.buildFrom(characterResponse)
            } ?: emptyList(),
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(request.bodyNullable?.info?.next)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getPageIndexFromNext(next: String?): Int?{
        if (next == null) {
            return null
        }

        val remainder = next.substringAfter("page=")
        val finalIndex = if (remainder.contains('&')) {
            remainder.indexOfFirst { it == '&' }
        } else {
            remainder.length
        }

        return remainder.substring(0, finalIndex).toIntOrNull()
    }

}