package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.util.UUID;
import java.util.function.BiFunction;

/**
 * Converts {@link PutUnitRequest} to {@link Unit}. Caution, some fields are not set as they should be updated
 * by business logic.
 */
public class RequestToUnitFunction implements BiFunction<UUID, PutUnitRequest, Unit> {

    @Override
    public Unit apply(UUID id, PutUnitRequest request) {
        return Unit.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .leadership(request.getLeadership())
                .movement(request.getMovement())
                .save(request.getSave())
                .toughness(request.getToughness())
                .wounds(request.getWounds())
                .build();
    }

}
