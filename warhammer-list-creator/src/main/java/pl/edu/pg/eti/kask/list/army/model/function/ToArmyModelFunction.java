package pl.edu.pg.eti.kask.list.army.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.model.ArmyModel;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ToArmyModelFunction implements Function<Army, ArmyModel>, Serializable {

    @Override
    public ArmyModel apply(Army army) {
        ArmyModel.ArmyModelBuilder builder = ArmyModel.builder()
                .id(army.getId())
                .name(army.getName())
                .description(army.getDescription());

        if (army.getSquads() != null) {
            for (Squad s : army.getSquads()) {
                builder.squad(ArmyModel.Squad.builder()
                        .id(s.getId())
                        .count(s.getCount())
                        .unitId(s.getUnit() != null ? s.getUnit().getId() : null)
                        .unitName(s.getUnit() != null ? s.getUnit().getName() : null)
                        .build());
            }
        }
        return builder.build();
    }
}