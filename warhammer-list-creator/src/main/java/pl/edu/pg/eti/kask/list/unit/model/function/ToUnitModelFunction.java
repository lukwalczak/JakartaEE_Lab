package pl.edu.pg.eti.kask.list.unit.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitModel;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ToUnitModelFunction implements Function<Unit, UnitModel>, Serializable {
    @Override
    public UnitModel apply(Unit unit) {
        return UnitModel.builder()
                .id(unit.getId())
                .name(unit.getName())
                .leadership(unit.getLeadership())
                .toughness(unit.getToughness())
                .wounds(unit.getWounds())
                .save(unit.getSave())
                .description(unit.getDescription())
                .build();
    }
}
