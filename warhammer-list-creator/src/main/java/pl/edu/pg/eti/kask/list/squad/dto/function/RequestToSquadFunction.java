package pl.edu.pg.eti.kask.list.squad.dto.function;

import pl.edu.pg.eti.kask.list.squad.dto.PutSquadRequest;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToSquadFunction implements BiFunction<UUID, PutSquadRequest, Squad> {

    @Override
    public Squad apply(UUID id, PutSquadRequest request) {
        return Squad.builder()
                .id(id)
                .count(request.getCount())
                .build();
    }
}
