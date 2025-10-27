package pl.edu.pg.eti.kask.list.unit.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitEditModel;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ToUnitEditModelFunction implements Function<Unit, UnitEditModel>, Serializable {
    @Override
    public UnitEditModel apply(Unit unit) {
        return UnitEditModel.builder()
                .name(unit.getName())
                .leadership(unit.getLeadership())
                .toughness(unit.getToughness())
                .wounds(unit.getWounds())
                .save(unit.getSave())
                .description(unit.getDescription())
                .build();
    }
}
