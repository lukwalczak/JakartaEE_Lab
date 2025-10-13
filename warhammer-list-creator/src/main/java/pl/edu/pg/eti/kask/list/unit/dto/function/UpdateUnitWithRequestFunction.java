package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Unit} based on provided value and updated with values from
 * {@link PatchUnitRequest}.
 */
public class UpdateUnitWithRequestFunction implements BiFunction<Unit, PatchUnitRequest, Unit> {

    @Override
    public Unit apply(Unit entity, PatchUnitRequest request) {
        return Unit.builder()
                .id(entity.getId())
                .name(request.getName())
                .description(request.getDescription())
                .leadership(request.getLeadership())
                .movement(request.getMovement())
                .save(request.getSave())
                .toughness(request.getToughness())
                .wounds(request.getWounds())
                .portrait(entity.getPortrait())
                .build();
    }

}
