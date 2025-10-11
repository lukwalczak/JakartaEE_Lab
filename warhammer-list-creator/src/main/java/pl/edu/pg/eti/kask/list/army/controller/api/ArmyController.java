package pl.edu.pg.eti.kask.list.army.controller.api;

import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.dto.PatchArmyRequest;
import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;

import java.util.UUID;

public interface ArmyController {

    GetArmyResponse getArmy(UUID id);

    GetArmiesResponse getArmies();

    GetArmiesResponse getArmies(UUID id);

    void putArmy(UUID id, PutArmyRequest request);

    void patchArmies(UUID id, PatchArmyRequest request);

    void deleteArmies(UUID id);

}
