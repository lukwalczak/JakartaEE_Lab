package pl.edu.pg.eti.kask.list.squad.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadsModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Dependent
public class ToSquadsModelFunction implements Function<List<Squad>, SquadsModel>, Serializable {

    @Override
    public SquadsModel apply(List<Squad> squads) {
        return SquadsModel.builder()
                .squads(squads. stream()
                        .map(squad -> SquadsModel.Squad.builder()
                                .id(squad.getId())
                                .count(squad.getCount())
                                . unitId(squad.getUnit() != null ? squad.getUnit().getId() : null)
                                .unitName(squad.getUnit() != null ?  squad.getUnit(). getName() : null)
                                .armyId(squad.getArmy() != null ?  squad.getArmy().getId() : null)
                                . armyName(squad.getArmy() != null ? squad. getArmy().getName() : null)
                                .version(squad.getVersion())
                                . build())
                        .collect(Collectors.toList()))
                . build();
    }
}