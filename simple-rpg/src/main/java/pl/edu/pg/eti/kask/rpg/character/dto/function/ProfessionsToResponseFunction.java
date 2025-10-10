package pl.edu.pg.eti.kask.rpg.character.dto.function;

import pl.edu.pg.eti.kask.rpg.character.dto.GetProfessionsResponse;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;

import java.util.List;
import java.util.function.Function;

/**
 * Converts {@link List<Profession>} to {@link GetProfessionsResponse}.
 */
public class ProfessionsToResponseFunction implements Function<List<Profession>, GetProfessionsResponse> {

    @Override
    public GetProfessionsResponse apply(List<Profession> entities) {
        return GetProfessionsResponse.builder()
                .professions(entities.stream()
                        .map(profession -> GetProfessionsResponse.Profession.builder()
                                .id(profession.getId())
                                .name(profession.getName())
                                .build())
                        .toList())
                .build();
    }

}
