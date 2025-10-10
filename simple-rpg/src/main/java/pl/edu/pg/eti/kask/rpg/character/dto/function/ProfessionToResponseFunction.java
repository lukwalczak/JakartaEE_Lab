package pl.edu.pg.eti.kask.rpg.character.dto.function;

import pl.edu.pg.eti.kask.rpg.character.dto.GetProfessionResponse;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts {@link Profession} to {@link GetProfessionResponse}.
 */
public class ProfessionToResponseFunction implements Function<Profession, GetProfessionResponse> {

    @Override
    public GetProfessionResponse apply(Profession entity) {
        return GetProfessionResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .skills(entity.getSkills().entrySet().stream()
                        .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        entry -> GetProfessionResponse.Skill.builder()
                                                .id(entry.getValue().getId())
                                                .name(entry.getValue().getDescription())
                                                .description(entry.getValue().getDescription())
                                                .build()
                                )
                        ))
                .build();
    }

}
