package pl.edu.pg.eti.kask.list.army.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.entity.Army;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

public class ArmyToResponseFunction implements Function<Army, GetArmyResponse> {

    @Override
    public GetArmyResponse apply(Army entity) {
        return GetArmyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .faction(entity.getFaction())
                .userSummary(GetArmyResponse.UserSummary.builder()
                        .id(entity.getOwner().getId())
                        .login(entity.getOwner().getLogin())
                        .build()
                )
                .squads(
                        Optional.ofNullable(entity.getSquads())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(squad -> GetArmyResponse.Squad.builder()
                                        .id(squad.getId())
                                        .unitName(squad.getUnit().getName())
                                        .count(squad.getCount())
                                        .build()
                                )
                                .toList()
                )
                .build();
    }

}
