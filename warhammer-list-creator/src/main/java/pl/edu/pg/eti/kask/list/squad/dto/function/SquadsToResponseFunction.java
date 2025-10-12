package pl.edu.pg.eti.kask.list.squad.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadResponse;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadsResponse;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.util.List;
import java.util.function.Function;

public class SquadsToResponseFunction implements Function<List<Squad>, GetSquadsResponse> {

    @Override
    public GetSquadsResponse apply(List<Squad> entities) {
        return GetSquadsResponse.builder()
                .squads(entities.stream()
                        .map(squad -> GetSquadsResponse.Squad.builder()
                                .id(squad.getId())
                                .count(squad.getCount())
                                .unit(
                                        GetSquadsResponse.Unit.builder()
                                                .id(squad.getUnit().getId())
                                                .name(squad.getUnit().getName())
                                                .build()
                                )
                                .build())
                        .toList())
                .build();
    }
}

