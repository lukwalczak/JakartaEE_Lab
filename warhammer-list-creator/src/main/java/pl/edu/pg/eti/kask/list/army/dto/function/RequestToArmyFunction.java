package pl.edu.pg.eti.kask.list.army.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutUnitRequest} to {@link Unit}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
public class RequestToArmyFunction implements BiFunction<UUID, PutArmyRequest, Army> {

    @Override
    public Army apply(UUID id, PutArmyRequest request) {
        return Army.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .squads(request.getSquads())
                .faction(request.getFaction())
                .build();
    }

}
