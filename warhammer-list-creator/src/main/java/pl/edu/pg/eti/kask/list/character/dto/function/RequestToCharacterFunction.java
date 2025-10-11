package pl.edu.pg.eti.kask.list.character.dto.function;

import pl.edu.pg.eti.kask.list.character.dto.PutCharacterRequest;
import pl.edu.pg.eti.kask.list.character.entity.Character;
import pl.edu.pg.eti.kask.list.character.entity.Profession;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutCharacterRequest} to {@link Character}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
public class RequestToCharacterFunction implements BiFunction<UUID, PutCharacterRequest, Character> {

    @Override
    public Character apply(UUID id, PutCharacterRequest request) {
        return Character.builder()
                .id(id)
                .name(request.getName())
                .background(request.getBackground())
                .age(request.getAge())
                .strength(request.getStrength())
                .constitution(request.getConstitution())
                .charisma(request.getCharisma())
                .health(request.getHealth())
                .profession(Profession.builder()
                        .id(request.getProfession())
                        .build())
                .build();
    }

}
