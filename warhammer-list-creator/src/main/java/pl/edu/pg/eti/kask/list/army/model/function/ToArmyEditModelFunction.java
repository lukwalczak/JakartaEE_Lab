package pl.edu.pg.eti.kask.list.army.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.model.ArmyEditModel;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ToArmyEditModelFunction implements Function<Army, ArmyEditModel>, Serializable {

    @Override
    public ArmyEditModel apply(Army army) {
        ArmyEditModel.ArmyEditModelBuilder builder = ArmyEditModel.builder()
                .id(army.getId())
                .name(army.getName())
                .description(army.getDescription());

        if (army.getSquads() != null) {
            for (Squad s : army.getSquads()) {
                builder.squad(ArmyEditModel.Squad.builder()
                        .squadId(s.getId())
                        .count(s.getCount())
                        .unitId(s.getUnit() != null ? s.getUnit().getId() : null)
                        .unitName(s.getUnit() != null ? s.getUnit().getName() : null)
                        .build());
            }
        }
        return builder.build();
    }
}