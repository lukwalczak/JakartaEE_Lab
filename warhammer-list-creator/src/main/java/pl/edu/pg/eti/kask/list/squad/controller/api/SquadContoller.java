package pl.edu.pg.eti.kask.list.squad.controller.api;

import pl.edu.pg.eti.kask.list.squad.dto.PutSquadRequest;

import java.util.UUID;

public interface SquadContoller {

    void putSquad(UUID id, PutSquadRequest request);

    void deleteSquad(UUID id);
}
