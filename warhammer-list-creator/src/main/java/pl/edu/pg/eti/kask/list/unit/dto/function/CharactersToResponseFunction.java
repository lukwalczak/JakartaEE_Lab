package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.entity.Character;

import java.util.List;
import java.util.function.Function;

/**
 * Coverts {@link List<Character>} to {@link GetUnitsResponse}.
 */
public class CharactersToResponseFunction implements Function<List<Character>, GetUnitsResponse> {

    @Override
    public GetUnitsResponse apply(List<Character> entities) {
        return GetUnitsResponse.builder()
                .characters(entities.stream()
                        .map(character -> GetUnitsResponse.Character.builder()
                                .id(character.getId())
                                .name(character.getName())
                                .build())
                        .toList())
                .build();
    }

}
