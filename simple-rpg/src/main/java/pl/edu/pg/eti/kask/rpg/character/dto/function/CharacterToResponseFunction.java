package pl.edu.pg.eti.kask.rpg.character.dto.function;

import pl.edu.pg.eti.kask.rpg.character.dto.GetCharacterResponse;
import pl.edu.pg.eti.kask.rpg.character.entity.Character;

import java.util.function.Function;

/**
 * Converts {@link Character} to {@link GetCharacterResponse}.
 */
public class CharacterToResponseFunction implements Function<Character, GetCharacterResponse> {

    @Override
    public GetCharacterResponse apply(Character entity) {
        return GetCharacterResponse.builder()
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
                .profession(GetCharacterResponse.Profession.builder()
                        .id(entity.getProfession().getId())
                        .name(entity.getProfession().getName())
                        .build())
                .build();
    }

}
