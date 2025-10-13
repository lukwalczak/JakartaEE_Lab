package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.util.function.Function;

/**
 * Converts {@link Unit} to {@link GetUnitResponse}.
 */
public class UnitToResponseFunction implements Function<Unit, GetUnitResponse> {

    @Override
    public GetUnitResponse apply(Unit entity) {
        return GetUnitResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .leadership(entity.getLeadership())
                .movement(entity.getMovement())
                .save(entity.getSave())
                .toughness(entity.getToughness())
                .wounds(entity.getWounds())
                .build();
    }

}
