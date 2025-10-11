package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Character;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Character} based on provided value and updated with values from
 * {@link PatchUnitRequest}.
 */
public class UpdateCharacterWithRequestFunction implements BiFunction<Character, PatchUnitRequest, Character> {

    @Override
    public Character apply(Character entity, PatchUnitRequest request) {
        return Character.builder()
                .id(entity.getId())
                .name(request.getName())
                .background(request.getBackground())
                .age(request.getAge())
                .strength(entity.getStrength())
                .constitution(entity.getConstitution())
                .charisma(entity.getCharisma())
                .health(entity.getHealth())
                .experience(entity.getExperience())
                .level(entity.getLevel())
                .profession(entity.getProfession())
                .portrait(entity.getPortrait())
                .build();
    }

}
