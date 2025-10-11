package pl.edu.pg.eti.kask.list.army.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.PatchArmyRequest;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link Unit} based on provided value and updated with values from
 * {@link PatchUnitRequest}.
 */
public class UpdateArmyWithRequestFunction implements BiFunction<Army, PatchArmyRequest, Army> {

    @Override
    public Army apply(Army entity, PatchArmyRequest request) {
        return Army.builder()
                .id(entity.getId())
                .name(request.getName())
                .description(request.getDescription())
                .faction(request.getFaction())
                .squads(request.getSquads())
                .build();
    }

}
