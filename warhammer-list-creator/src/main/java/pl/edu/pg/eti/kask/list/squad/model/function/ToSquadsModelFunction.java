package pl.edu.pg.eti.kask.list.squad.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadsModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

@Dependent
public class ToSquadsModelFunction implements Function<List<Squad>, SquadsModel>, Serializable {

    @Override
    public SquadsModel apply(List<Squad> entities) {
        SquadsModel.SquadsModelBuilder builder = SquadsModel.builder();
        entities.forEach(s -> builder.squad(SquadsModel.Squad.builder()
                .id(s.getId())
                .count(s.getCount())
                .unitId(s.getUnit() != null ? s.getUnit().getId() : null)
                .unitName(s.getUnit() != null ? s.getUnit().getName() : null)
                .armyId(s.getArmy() != null ? s.getArmy().getId() : null)
                .armyName(s.getArmy() != null ? s.getArmy().getName() : null)
                .build()));
        return builder.build();
    }
}