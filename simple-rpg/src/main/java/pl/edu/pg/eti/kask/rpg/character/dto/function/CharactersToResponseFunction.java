package pl.edu.pg.eti.kask.rpg.character.dto.function;

import pl.edu.pg.eti.kask.rpg.character.dto.GetCharactersResponse;
import pl.edu.pg.eti.kask.rpg.character.entity.Character;

import java.util.List;
import java.util.function.Function;

/**
 * Coverts {@link List<Character>} to {@link GetCharactersResponse}.
 */
public class CharactersToResponseFunction implements Function<List<Character>, GetCharactersResponse> {

    @Override
    public GetCharactersResponse apply(List<Character> entities) {
        return GetCharactersResponse.builder()
                .characters(entities.stream()
                        .map(character -> GetCharactersResponse.Character.builder()
                                .id(character.getId())
                                .name(character.getName())
                                .build())
                        .toList())
                .build();
    }

}
