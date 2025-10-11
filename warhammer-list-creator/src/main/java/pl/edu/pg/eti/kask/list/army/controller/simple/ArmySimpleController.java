package pl.edu.pg.eti.kask.list.army.controller.simple;

import pl.edu.pg.eti.kask.list.army.controller.api.ArmyController;
import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.dto.PatchArmyRequest;
import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;

import java.util.UUID;

public class ArmySimpleController implements ArmyController {

    private final ArmyService service;

    private final DtoFunctionFactory factory;

    public ArmySimpleController(ArmyService armyService, DtoFunctionFactory factory) {
        this.service = armyService;
        this.factory = factory;
    }

    @Override
    public GetArmyResponse getArmy(UUID id) {
        return service.find(id).map(factory.armyToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetArmiesResponse getArmies() {
        return factory.armiesToResponseFunction().apply(service.findAll());
    }

    @Override
    public GetArmiesResponse getArmies(UUID id) {
        return factory.armiesToResponseFunction().apply(service.findAll(id));
    }

    @Override
    public void putArmies(UUID id, PutArmyRequest request) {

    }

    @Override
    public void patchArmies(UUID id, PatchArmyRequest request) {

    }

    @Override
    public void deleteArmies(UUID id) {

    }

}
