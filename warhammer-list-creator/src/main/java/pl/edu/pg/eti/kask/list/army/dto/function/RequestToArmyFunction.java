package pl.edu.pg.eti.kask.list.army.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;
import pl.edu.pg.eti.kask.list.army.entity.Army;

import java.util.UUID;
import java.util.function.BiFunction;


public class RequestToArmyFunction implements BiFunction<UUID, PutArmyRequest, Army> {


    @Override
    public Army apply(UUID id, PutArmyRequest request) {
        return Army.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

}
