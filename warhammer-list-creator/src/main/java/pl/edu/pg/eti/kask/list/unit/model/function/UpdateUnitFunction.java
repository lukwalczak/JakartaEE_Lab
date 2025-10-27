package pl.edu.pg.eti.kask.list.unit.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

@Dependent
public class UpdateUnitFunction implements BiFunction<Unit, UnitEditModel, Unit>, Serializable {
    @Override
    public Unit apply(Unit unit, UnitEditModel request) {
        return Unit.builder()
                .id(unit.getId())
                .portrait(unit.getPortrait())
                .name(request.getName())
                .leadership(request.getLeadership())
                .toughness(request.getToughness())
                .wounds(request.getWounds())
                .save(request.getSave())
                .description(request.getDescription())
                .build();
    }
}
