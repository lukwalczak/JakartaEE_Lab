package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.entity.Character;

import java.util.function.Function;

/**
 * Converts {@link Character} to {@link GetUnitResponse}.
 */
public class CharacterToResponseFunction implements Function<Character, GetUnitResponse> {

    @Override
    public GetUnitResponse apply(Character entity) {
        return GetUnitResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .background(entity.getBackground())
                .age(entity.getAge())
                .strength(entity.getStrength())
                .constitution(entity.getConstitution())
                .charisma(entity.getCharisma())
                .health(entity.getHealth())
                .level(entity.getLevel())
                .experience(entity.getExperience())
                .profession(GetUnitResponse.Profession.builder()
                        .id(entity.getProfession().getId())
                        .name(entity.getProfession().getName())
                        .build())
                .build();
    }

}
