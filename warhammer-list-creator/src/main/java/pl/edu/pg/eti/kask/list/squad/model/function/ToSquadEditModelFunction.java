package pl.edu.pg.eti.kask.list.squad.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadEditModel;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ToSquadEditModelFunction implements Function<Squad, SquadEditModel>, Serializable {

    @Override
    public SquadEditModel apply(Squad squad) {
        return SquadEditModel.builder()
                .id(squad.getId())
                .count(squad.getCount())
                .armyId(squad.getArmy() != null ? squad.getArmy().getId() : null)
                .armyName(squad.getArmy() != null ? squad.getArmy().getName() : null)
                .unitId(squad.getUnit() != null ? squad.getUnit().getId() : null)
                .unitName(squad.getUnit() != null ? squad.getUnit().getName() : null)
                .build();
    }
}