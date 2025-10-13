package pl.edu.pg.eti.kask.list.squad.dto.function;

import pl.edu.pg.eti.kask.list.squad.dto.PatchSquadRequest;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.util.function.BiFunction;

public class UpdateSquadWithRequestFunction implements BiFunction<Squad, PatchSquadRequest, Squad> {

    @Override
    public Squad apply(Squad squad, PatchSquadRequest patchSquadRequest) {
        return Squad.builder()
                .id(squad.getId())
                .army(squad.getArmy())
                .unit(squad.getUnit())
                .count(patchSquadRequest.getCount())
                .build();
    }
}
