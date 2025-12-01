package pl.edu.pg.eti.kask.list.army.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.entity.Army;

import java.util.List;
import java.util.function.Function;


public class ArmiesToResponseFunction implements Function<List<Army>, GetArmiesResponse> {

    @Override
    public GetArmiesResponse apply(List<Army> entities) {
        return GetArmiesResponse.builder()
                .armies(entities.stream()
                        .map(army -> GetArmiesResponse.Army.builder()
                                .id(army.getId())
                                .name(army.getName())
                                .build())
                        .toList())
                .build();
    }

}
