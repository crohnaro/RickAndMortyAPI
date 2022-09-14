package domain.mappers

import com.example.rickandmortyapi.GenerateCharacterByIdResponse
import domain.models.Character

object CharacterMapper {

    fun buildFrom(response: GenerateCharacterByIdResponse): Character{
        return Character(
            episodeList = emptyList(), // TODO
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }
}