package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Character;
import pl.edu.pg.eti.kask.list.unit.entity.Profession;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutUnitRequest} to {@link Character}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
public class RequestToCharacterFunction implements BiFunction<UUID, PutUnitRequest, Character> {

    @Override
    public Character apply(UUID id, PutUnitRequest request) {
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
