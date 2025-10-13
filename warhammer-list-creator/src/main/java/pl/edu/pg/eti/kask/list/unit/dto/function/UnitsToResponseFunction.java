package pl.edu.pg.eti.kask.list.unit.dto.function;

import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.util.List;
import java.util.function.Function;

/**
 * Coverts {@link List< Unit >} to {@link GetUnitsResponse}.
 */
public class UnitsToResponseFunction implements Function<List<Unit>, GetUnitsResponse> {

    @Override
    public GetUnitsResponse apply(List<Unit> entities) {
        return GetUnitsResponse.builder()
                .units(entities.stream()
                        .map(character -> GetUnitsResponse.Unit.builder()
                                .id(character.getId())
                                .name(character.getName())
                                .build())
                        .toList())
                .build();
    }

}
