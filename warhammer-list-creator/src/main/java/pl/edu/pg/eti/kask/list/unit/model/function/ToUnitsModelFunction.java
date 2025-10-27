package pl.edu.pg.eti.kask.list.unit.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;

import java.util.List;
import java.util.function.Function;

@Dependent
public class ToUnitsModelFunction implements Function<List<Unit>, UnitsModel> {


    @Override
    public UnitsModel apply(List<Unit> units) {
        return UnitsModel.builder()
                .units(
                        units.stream()
                                .map(unit -> UnitsModel.Unit.builder()
                                        .id(unit.getId())
                                        .name(unit.getName())
                                        .build()
                                )
                                .toList()
                )
                .build();
    }
}
