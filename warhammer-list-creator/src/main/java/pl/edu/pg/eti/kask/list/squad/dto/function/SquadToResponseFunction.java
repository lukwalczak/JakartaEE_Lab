package pl.edu.pg.eti.kask.list.squad.dto.function;

import pl.edu.pg.eti.kask.list.squad.dto.GetSquadResponse;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.util.function.Function;

public class SquadToResponseFunction implements Function<Squad, GetSquadResponse> {

    @Override
    public GetSquadResponse apply(Squad squad) {
        return GetSquadResponse.builder()
                .id(squad.getId())
                .count(squad.getCount())
                .army(
                        GetSquadResponse.Army.builder()
                                .id(squad.getArmy().getId())
                                .name(squad.getArmy().getName())
                                .build()
                )
                .unit(
                        GetSquadResponse.Unit.builder()
                                .id(squad.getUnit().getId())
                                .name(squad.getUnit().getName())
                                .build()
                )
                .build();
    }
}
