package pl.edu.pg.eti.kask.list.unit.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitCreateModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ModelToUnitFunction implements Function<UnitCreateModel, Unit>, Serializable {
    @Override
    public Unit apply(UnitCreateModel unitCreateModel) {
        try {
            return Unit.builder()
                    .id(unitCreateModel.getId())
                    .name(unitCreateModel.getName())
                    .leadership(unitCreateModel.getLeadership())
                    .toughness(unitCreateModel.getToughness())
                    .wounds(unitCreateModel.getWounds())
                    .save(unitCreateModel.getSave())
                    .description(unitCreateModel.getDescription())
                    .portrait(unitCreateModel.getPortrait() != null
                            ? unitCreateModel.getPortrait().getInputStream().readAllBytes()
                            : null)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
