package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.GetProfessionsResponse;
import pl.edu.pg.eti.kask.list.unit.entity.Profession;

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
