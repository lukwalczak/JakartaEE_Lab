package pl.edu.pg.eti.kask.list.army.controller.rest;


import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.list.army.controller.api.ArmyController;
import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.dto.PatchArmyRequest;
import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;

import java.util.UUID;

@Path("")
public class RestArmyController implements ArmyController {
    private ArmyService service;

    private SquadService squadService;

    private final DtoFunctionFactory factory;

    @Inject
    public RestArmyController(ArmyService armyService, SquadService squadService, DtoFunctionFactory factory) {
        this.service = armyService;
        this.squadService = squadService;
        this.factory = factory;
    }

    @EJB
    public void setService(ArmyService service) {
        this.service = service;
    }

    @EJB
    public void setSquadService(SquadService squadService) {
        this.squadService = squadService;
    }

    @Override
    public GetArmyResponse getArmy(UUID id) {
        return service.find(id)
                .map(factory.armyToResponse())
                .map(response -> {
                    response.setSquads(
                            squadService.findByArmyId(id).stream()
                                    .map(squad -> GetArmyResponse.Squad.builder()
                                            .id(squad.getId())
                                            .unitName(squad.getUnit().getName())
                                            .count(squad.getCount())
                                            .build()
                                    )
                                    .toList()
                    );
                    return response;
                })
                .orElseThrow(NotFoundException::new);
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
    public void putArmy(UUID id, PutArmyRequest request) {
        try {
            service.create(factory.requestToArmy().apply(id, request));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void putArmy(UUID id, PutArmyRequest request, UUID userId) {
        try {
            service.create(factory.requestToArmy().apply(id, request), userId);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void patchArmy(UUID id, PatchArmyRequest request) {

    }

    @Override
    public void deleteArmy(UUID id) {
        service.find(id).ifPresentOrElse(
                service::delete,
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public boolean armyExists(UUID id) {
        return service.exists(id);
    }

}
