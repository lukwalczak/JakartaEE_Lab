package pl.edu.pg.eti.kask.list.squad.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadModel;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ToSquadModelFunction implements Function<Squad, SquadModel>, Serializable {

    @Override
    public SquadModel apply(Squad squad) {
        return SquadModel.builder()
                .id(squad.getId())
                .count(squad.getCount())
                .armyId(squad.getArmy() != null ? squad.getArmy().getId() : null)
                .armyName(squad.getArmy() != null ? squad.getArmy().getName() : null)
                .unitId(squad.getUnit() != null ? squad.getUnit().getId() : null)
                .unitName(squad.getUnit() != null ? squad.getUnit().getName() : null)
                .build();
    }
}