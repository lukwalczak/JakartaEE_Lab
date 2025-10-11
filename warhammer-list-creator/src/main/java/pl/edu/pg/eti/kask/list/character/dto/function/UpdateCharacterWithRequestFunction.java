package pl.edu.pg.eti.kask.list.character.dto.function;

import pl.edu.pg.eti.kask.list.character.dto.PatchCharacterRequest;
import pl.edu.pg.eti.kask.list.character.entity.Character;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Character} based on provided value and updated with values from
 * {@link PatchCharacterRequest}.
 */
public class UpdateCharacterWithRequestFunction implements BiFunction<Character, PatchCharacterRequest, Character> {

    @Override
    public Character apply(Character entity, PatchCharacterRequest request) {
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
